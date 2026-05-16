package com.onlikee.service.lightoss.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.pojo.dto.request.LightOssPublishedSiteDort;
import com.onlikee.service.lightoss.LightOssPublishService;

@Service
// 负责和 Light OSS 静态站点服务交互：
// 1. 确保 bucket 存在
// 2. 发布单文件 HTML 或 ZIP 站点
// 3. 在后续业务失败时按发布结果做补偿清理
public class LightOssPublishServiceImpl implements LightOssPublishService {
    private static final Logger log = LoggerFactory.getLogger(LightOssPublishServiceImpl.class);

    // 当前业务域名固定拼到 onlikee.cn 下。
    private static final String SITE_DOMAIN_SUFFIX = ".onlikee.cn";
    // ZIP 站点统一要求入口文件名为 index.html。
    private static final String INDEX_DOCUMENT = "index.html";

    private final RestClient lightOssRestClient;

    @Autowired
    public LightOssPublishServiceImpl(@Qualifier("lightOssRestClient") RestClient lightOssRestClient) {
        this.lightOssRestClient = lightOssRestClient;
    }

    @Override
    // bucket 以用户 uuid 为粒度。Light OSS 若返回 bucket_exists，按幂等成功处理。
    public void ensureBucketExists(String bucketName) {
        try {
            lightOssRestClient.post()
                    .uri("/api/v1/buckets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JSON.toJSONString(Map.of("name", bucketName)))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            if ("bucket_exists".equals(extractErrorCode(ex.getResponseBodyAsString()))) {
                return;
            }
            throw translatePublishException(ex, false);
        } catch (Exception ex) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }
    }

    @Override
    // 直接把单个 HTML 文件发布成站点。
    // 这里依赖 Light OSS 的 publish/file 接口自动处理入口文件信息。
    public LightOssPublishedSiteDort publishHtml(String bucketName, String appEnglishName, MultipartFile appFile) {
        String originalFilename = requireFilename(appFile);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("bucket", bucketName);
        builder.part("parent_prefix", appEnglishName);
        builder.part("domains", JSON.toJSONString(List.of(buildDomain(appEnglishName))));
        builder.part("enabled", "true");
        builder.part("error_document", "");
        builder.part("spa_fallback", "true");
        builder.part("file", appFile.getResource())
                .filename(originalFilename)
                .contentType(resolveMediaType(appFile.getContentType()));

        JSONObject data = postMultipart("/api/v1/sites/publish/file", builder.build(), false);
        return toPublishedSiteDort(data, data.getString("index_document"));
    }

    @Override
    // ZIP 站点发布流程：
    // 1. 解压到临时目录
    // 2. 校验包结构并生成上传清单
    // 3. 调 Light OSS 批量发布接口
    // 4. 无论成功失败都清理本地临时目录
    public LightOssPublishedSiteDort publishZipSite(String bucketName, String appEnglishName, MultipartFile appFile) {
        Path tempDir;
        try {
            tempDir = Files.createTempDirectory("onlikee-site-publish-");
        } catch (IOException ex) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }

        try {
            List<UploadManifestItem> manifestItems = prepareZipManifestItems(appFile, tempDir);

            // Light OSS 批量发布接口要求 manifest 描述每个 multipart 字段和目标相对路径。
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("bucket", bucketName);
            builder.part("parent_prefix", appEnglishName);
            builder.part("domains", JSON.toJSONString(List.of(buildDomain(appEnglishName))));
            builder.part("enabled", "true");
            builder.part("index_document", INDEX_DOCUMENT);
            builder.part("error_document", "");
            builder.part("spa_fallback", "true");
            builder.part("manifest", JSON.toJSONString(toManifestJsonArray(manifestItems)));

            for (UploadManifestItem manifestItem : manifestItems) {
                builder.part(manifestItem.fileField(), new FileSystemResource(manifestItem.filePath()))
                        .filename(manifestItem.filePath().getFileName().toString())
                        .contentType(resolveMediaType(detectContentType(manifestItem.filePath())));
            }

            JSONObject data = postMultipart("/api/v1/sites/publish", builder.build(), true);
            return toPublishedSiteDort(data.getJSONObject("site"), INDEX_DOCUMENT);
        } finally {
            deleteDirectoryQuietly(tempDir);
        }
    }

    @Override
    // 应用创建流程是“先发布站点，再写数据库”。
    // 因此后续任一步失败时，需要根据发布结果把站点和对象尽量清理掉。
    public void cleanupPublishedSite(LightOssPublishedSiteDort publishedSite) {
        if (publishedSite == null) {
            return;
        }

        // 站点删除接口和对象删除接口都尽量执行；即使其中一步失败，也不要中断补偿流程。
        if (publishedSite.getSiteId() != null) {
            deleteSiteQuietly(publishedSite.getSiteId());
        }

        if (!StringUtils.hasText(publishedSite.getBucketName())) {
            return;
        }

        // ZIP 站点需要优先按根目录递归清理，否则只删入口文件会遗留静态资源。
        if (StringUtils.hasText(publishedSite.getRootPrefix())) {
            deleteFolderQuietly(publishedSite.getBucketName(), publishedSite.getRootPrefix());
            return;
        }

        if (StringUtils.hasText(publishedSite.getObjectKey())) {
            deleteObjectQuietly(publishedSite.getBucketName(), publishedSite.getObjectKey());
        }
    }

    // 统一封装 multipart 发布请求，确保所有发布入口共享同一套响应解析和异常翻译逻辑。
    private JSONObject postMultipart(String uri, MultiValueMap<String, ?> body, boolean packageValidationEnabled) {
        try {
            ResponseEntity<String> response = lightOssRestClient.post()
                    .uri(uri)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);
            return extractDataObject(response.getBody());
        } catch (RestClientResponseException ex) {
            throw translatePublishException(ex, packageValidationEnabled);
        } catch (Exception ex) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }
    }

    // 把 Light OSS 返回的站点 JSON 收敛成业务侧需要的最小结果对象。
    // objectKey 主要用于“单文件站点”或无法按目录删除时的补偿清理。
    private LightOssPublishedSiteDort toPublishedSiteDort(JSONObject siteJson, String fallbackIndexDocument) {
        if (siteJson == null) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }

        String rootPrefix = siteJson.getString("root_prefix");
        String indexDocument = siteJson.getString("index_document");
        if (!StringUtils.hasText(indexDocument)) {
            indexDocument = fallbackIndexDocument;
        }

        LightOssPublishedSiteDort publishedSite = new LightOssPublishedSiteDort();
        publishedSite.setSiteId(siteJson.getLong("id"));
        publishedSite.setBucketName(siteJson.getString("bucket"));
        publishedSite.setRootPrefix(rootPrefix);
        publishedSite.setObjectKey(buildObjectKey(rootPrefix, indexDocument));
        return publishedSite;
    }

    // 校验 ZIP 包结构并生成上传清单。
    // 当前支持两种结构：
    // 1. 压缩包根目录直接包含 index.html 与静态资源
    // 2. 压缩包外层只有一个构建目录，该目录内包含 index.html 与静态资源
    private List<UploadManifestItem> prepareZipManifestItems(MultipartFile appFile, Path tempDir) {
        String originalFilename = requireFilename(appFile);
        if (!originalFilename.toLowerCase().endsWith(".zip")) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包必须是 zip 压缩包");
        }

        try {
            extractZip(appFile.getInputStream(), tempDir);
            List<Path> regularFiles = listRegularFiles(tempDir);
            if (regularFiles.isEmpty()) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包不能为空");
            }

            // 兼容两种常见结构：zip 根目录直接是站点文件，或外层仅包一层构建目录。
            Path rootIndex = tempDir.resolve(INDEX_DOCUMENT);
            if (Files.isRegularFile(rootIndex)) {
                // 根目录直出的构建包，上传到站点根目录下的 dist/ 前缀。
                return buildManifestItems(tempDir, regularFiles, "dist/");
            }

            List<Path> topLevelEntries = listTopLevelEntries(tempDir);
            if (topLevelEntries.size() != 1 || !Files.isDirectory(topLevelEntries.get(0))) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包结构无效");
            }

            Path topLevelDir = topLevelEntries.get(0);
            if (!Files.isRegularFile(topLevelDir.resolve(INDEX_DOCUMENT))) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包缺少 index.html");
            }

            // 单目录包保留原始目录层级上传。
            return buildManifestItems(tempDir, regularFiles, "");
        } catch (BizException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包无效");
        }
    }

    // 以 UTF-8 解压 ZIP，并把每个条目落到临时目录。
    // 具体的路径合法性在 normalizeZipEntryName / resolveZipEntryPath 中校验。
    private void extractZip(InputStream inputStream, Path tempDir) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream, StandardCharsets.UTF_8)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = normalizeZipEntryName(entry.getName());
                if (!StringUtils.hasText(entryName)) {
                    zipInputStream.closeEntry();
                    continue;
                }

                Path targetPath = resolveZipEntryPath(tempDir, entryName);
                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                    zipInputStream.closeEntry();
                    continue;
                }

                Path parent = targetPath.getParent();
                if (parent != null) {
                    Files.createDirectories(parent);
                }

                Files.copy(zipInputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                zipInputStream.closeEntry();
            }
        }
    }

    // 统一标准化 zip entry 路径：
    // 1. 兼容 Windows 反斜杠
    // 2. 去掉首尾无效分隔符
    // 3. 拒绝绝对路径、盘符路径、. / .. 等危险段
    private String normalizeZipEntryName(String entryName) {
        if (!StringUtils.hasText(entryName)) {
            return "";
        }

        String normalized = entryName.replace('\\', '/').trim();
        if (normalized.startsWith("/") || normalized.startsWith("\\") || normalized.matches("^[A-Za-z]:.*")) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包包含非法路径");
        }
        normalized = normalized.replaceAll("/+$", "");
        if (!StringUtils.hasText(normalized)) {
            return "";
        }

        String[] segments = normalized.split("/");
        for (String segment : segments) {
            if (!StringUtils.hasText(segment) || ".".equals(segment) || "..".equals(segment)) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包包含非法路径");
            }
        }

        return normalized;
    }

    // 再做一次 zip slip 防御，确保最终写入路径仍然落在临时目录内部。
    private Path resolveZipEntryPath(Path tempDir, String entryName) {
        Path targetPath = tempDir.resolve(entryName).normalize();
        if (!targetPath.startsWith(tempDir)) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包包含非法路径");
        }
        return targetPath;
    }

    // 收集解压后的所有普通文件，后续用于构造批量上传 manifest。
    private List<Path> listRegularFiles(Path rootDir) throws IOException {
        try (Stream<Path> stream = Files.walk(rootDir)) {
            return stream.filter(Files::isRegularFile)
                    // 忽略 macOS 打包产生的元数据文件，避免把合法应用包误判成非法结构。
                    .filter(path -> !isIgnoredArchivePath(rootDir, path))
                    .sorted(Comparator.comparing(path -> rootDir.relativize(path).toString()))
                    .toList();
        }
    }

    // 只看顶层目录结构，用于判断“根目录直出”还是“外包一层构建目录”。
    private List<Path> listTopLevelEntries(Path rootDir) throws IOException {
        try (Stream<Path> stream = Files.list(rootDir)) {
            return stream.filter(path -> !isIgnoredArchivePath(rootDir, path))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .toList();
        }
    }

    // 过滤掉压缩包里的系统元数据，避免影响结构校验和上传清单。
    private boolean isIgnoredArchivePath(Path rootDir, Path path) {
        Path relativePath = rootDir.relativize(path);
        if (relativePath.getNameCount() == 0) {
            return false;
        }

        // Finder 压缩包常会额外带上 __MACOSX、.DS_Store、._* 这类元数据。
        for (Path segment : relativePath) {
            if ("__MACOSX".equals(segment.toString())) {
                return true;
            }
        }

        String fileName = path.getFileName().toString();
        return ".DS_Store".equals(fileName) || fileName.startsWith("._");
    }

    // 按 Light OSS 批量发布接口要求，把本地文件映射成 multipart 字段名和目标相对路径。
    private List<UploadManifestItem> buildManifestItems(Path rootDir, List<Path> regularFiles, String prefix) {
        List<UploadManifestItem> manifestItems = new ArrayList<>();
        for (int i = 0; i < regularFiles.size(); i++) {
            Path file = regularFiles.get(i);
            String relativePath = rootDir.relativize(file).toString().replace('\\', '/');
            manifestItems.add(new UploadManifestItem("file_" + i, prefix + relativePath, file));
        }
        return manifestItems;
    }

    // manifest 是接口要求的 JSON 数组，每项描述一个 multipart 文件字段对应上传到哪里。
    private JSONArray toManifestJsonArray(List<UploadManifestItem> manifestItems) {
        JSONArray manifestJsonArray = new JSONArray();
        for (UploadManifestItem manifestItem : manifestItems) {
            JSONObject manifestJson = new JSONObject();
            manifestJson.put("file_field", manifestItem.fileField());
            manifestJson.put("relative_path", manifestItem.relativePath());
            manifestJsonArray.add(manifestJson);
        }
        return manifestJsonArray;
    }

    // 站点删除属于补偿动作，只记录日志，不把清理失败再向上抛。
    private void deleteSiteQuietly(Long siteId) {
        try {
            lightOssRestClient.delete()
                    .uri("/api/v1/sites/{siteId}", siteId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() != 404) {
                log.warn("light oss delete site failed: siteId={}, body={}", siteId, ex.getResponseBodyAsString());
            }
        } catch (Exception ex) {
            log.warn("light oss delete site failed: siteId={}", siteId, ex);
        }
    }

    // 对象删除用于清理单文件站点或目录信息不完整时的兜底删除。
    private void deleteObjectQuietly(String bucketName, String objectKey) {
        try {
            lightOssRestClient.delete()
                    .uri("/api/v1/buckets/" + encodePathSegment(bucketName) + "/objects/" + encodeObjectKey(objectKey))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() != 404) {
                log.warn("light oss delete object failed: bucket={}, objectKey={}, body={}",
                        bucketName, objectKey, ex.getResponseBodyAsString());
            }
        } catch (Exception ex) {
            log.warn("light oss delete object failed: bucket={}, objectKey={}", bucketName, objectKey, ex);
        }
    }

    // 目录删除用于清理 ZIP 站点，recursive=true 代表把目录下所有对象一起删掉。
    private void deleteFolderQuietly(String bucketName, String folderPath) {
        try {
            lightOssRestClient.delete()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/buckets/{bucket}/folders")
                            .queryParam("path", folderPath)
                            .queryParam("recursive", true)
                            .build(bucketName))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() != 404) {
                log.warn("light oss delete folder failed: bucket={}, folderPath={}, body={}",
                        bucketName, folderPath, ex.getResponseBodyAsString());
            }
        } catch (Exception ex) {
            log.warn("light oss delete folder failed: bucket={}, folderPath={}", bucketName, folderPath, ex);
        }
    }

    // Light OSS 响应按 { data, error } 包装，发布成功时业务方只关心 data 对象。
    private JSONObject extractDataObject(String responseBody) {
        JSONObject envelope = parseJsonObject(responseBody);
        JSONObject data = envelope.getJSONObject("data");
        if (data == null) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }
        return data;
    }

    // 空响应体按空 JSON 处理，交给上层决定是不是缺少必要字段。
    private JSONObject parseJsonObject(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return new JSONObject();
        }
        return JSON.parseObject(responseBody);
    }

    // 把 Light OSS 的错误码翻译成后端自己的业务异常：
    // - 域名冲突 => 应用英文名重复
    // - 包校验相关错误 => 应用包无效
    // - 其他错误 => 应用发布失败
    private BizException translatePublishException(RestClientResponseException ex, boolean packageValidationEnabled) {
        String responseBody = ex.getResponseBodyAsString();
        String errorCode = extractErrorCode(responseBody);
        String errorMessage = extractErrorMessage(responseBody);

        if ("domain_conflict".equals(errorCode)) {
            return new BizException(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS);
        }

        if (packageValidationEnabled
                && ("invalid_request".equals(errorCode)
                || "invalid_batch_manifest".equals(errorCode)
                || "batch_file_missing".equals(errorCode))) {
            return new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID,
                    StringUtils.hasText(errorMessage) ? errorMessage : ErrorCode.APPLICATION_PACKAGE_INVALID.getMessage());
        }

        if (StringUtils.hasText(errorMessage)) {
            return new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED, errorMessage);
        }
        return new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
    }

    // 只抽取错误码，方便上层做分支翻译。
    private String extractErrorCode(String responseBody) {
        JSONObject error = extractErrorObject(responseBody);
        return error == null ? null : error.getString("code");
    }

    // 只抽取错误文案，优先透传给前端更具体的失败原因。
    private String extractErrorMessage(String responseBody) {
        JSONObject error = extractErrorObject(responseBody);
        return error == null ? null : error.getString("message");
    }

    // Light OSS 错误对象统一位于 envelope.error。
    private JSONObject extractErrorObject(String responseBody) {
        JSONObject envelope = parseJsonObject(responseBody);
        return envelope.getJSONObject("error");
    }

    // 站点访问域名由应用英文名直接拼接固定后缀得到。
    private String buildDomain(String appEnglishName) {
        return appEnglishName + SITE_DOMAIN_SUFFIX;
    }

    // 发布接口依赖原始文件名；缺少文件名时按无效应用包处理。
    private String requireFilename(MultipartFile appFile) {
        String originalFilename = appFile.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "上传文件缺少文件名");
        }
        return originalFilename;
    }

    // objectKey 用于对象删除。
    // rootPrefix 为空时说明入口文件直接挂在站点根下。
    private String buildObjectKey(String rootPrefix, String indexDocument) {
        if (!StringUtils.hasText(indexDocument)) {
            return null;
        }
        if (!StringUtils.hasText(rootPrefix)) {
            return indexDocument;
        }
        return rootPrefix + indexDocument;
    }

    // 尝试探测文件类型；探测失败时交给上层用默认二进制类型兜底。
    private String detectContentType(Path filePath) {
        try {
            return Files.probeContentType(filePath);
        } catch (IOException ex) {
            return null;
        }
    }

    // Light OSS 需要合法的 MediaType；无法识别时按通用二进制流上传。
    private MediaType resolveMediaType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }

        try {
            return MediaType.parseMediaType(contentType);
        } catch (Exception ex) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    // objectKey 包含多级路径时需要逐段编码，避免把分隔符 '/' 一起编码掉。
    private String encodeObjectKey(String objectKey) {
        String[] segments = objectKey.split("/");
        List<String> encodedSegments = new ArrayList<>(segments.length);
        for (String segment : segments) {
            encodedSegments.add(encodePathSegment(segment).replace(".", "%2E"));
        }
        return String.join("/", encodedSegments);
    }

    // 统一做 URL 路径段编码，并把空格编码成 %20。
    private String encodePathSegment(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    // 清理 ZIP 解压产生的临时目录。
    // 这是本地资源回收动作，失败只记录日志，不能覆盖真正的发布异常。
    private void deleteDirectoryQuietly(Path directory) {
        if (directory == null || !Files.exists(directory)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(directory)) {
            stream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    log.warn("delete temp directory failed: path={}", path, ex);
                }
            });
        } catch (IOException ex) {
            log.warn("delete temp directory failed: path={}", directory, ex);
        }
    }

    // 对应 Light OSS 批量发布接口 manifest 中的一项。
    private record UploadManifestItem(String fileField, String relativePath, Path filePath) {
    }
}
