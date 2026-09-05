package com.onlikee.service.application.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.lightoss.ExplorerClient;
import com.onlikee.lightoss.LightOssClient;
import com.onlikee.lightoss.ObjectClient;
import com.onlikee.lightoss.SiteClient;
import com.onlikee.lightoss.exception.LightOssApiException;
import com.onlikee.lightoss.exception.LightOssException;
import com.onlikee.lightoss.exception.LightOssValidationException;
import com.onlikee.lightoss.transfer.UploadSource;
import com.onlikee.service.application.ApplicationSitePublishService;
import com.onlikee.util.UrlUtils;

@Service
// 负责应用站点的 Light OSS 发布适配：保留应用包规则和补偿语义，HTTP 细节交给 SDK。
public class ApplicationSitePublishServiceImpl implements ApplicationSitePublishService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSitePublishServiceImpl.class);

    private static final String INDEX_DOCUMENT = "index.html";

    private final LightOssClient lightOssClient;

    @Autowired
    public ApplicationSitePublishServiceImpl(LightOssClient lightOssClient) {
        this.lightOssClient = lightOssClient;
    }

    @Override
    public PublishedSite publish(String bucketName, String appSubDomain, MultipartFile appFile) {
        ensureBucketExists(bucketName);
        return publishZipSite(bucketName, appSubDomain, appFile);
    }

    @Override
    public void cleanupPublishedSite(PublishedSite publishedSite) {
        if (publishedSite == null) {
            return;
        }

        // 站点删除和对象删除都尽量执行；任一步失败都不应中断后续补偿。
        deleteSiteQuietly(publishedSite.siteId());

        if (!StringUtils.hasText(publishedSite.bucketName())) {
            return;
        }

        if (StringUtils.hasText(publishedSite.rootPrefix())) {
            deleteFolderQuietly(publishedSite.bucketName(), publishedSite.rootPrefix());
            return;
        }

        if (StringUtils.hasText(publishedSite.objectKey())) {
            deleteObjectQuietly(publishedSite.bucketName(), publishedSite.objectKey());
        }
    }

    // bucket 以用户 uuid 为粒度；已存在时按幂等成功处理。
    private void ensureBucketExists(String bucketName) {
        try {
            lightOssClient.buckets().create(bucketName);
        } catch (LightOssApiException exception) {
            if ("bucket_exists".equals(exception.code())) {
                return;
            }
            throw translateApiException(exception, false);
        } catch (LightOssException exception) {
            throw publishFailed(exception);
        }
    }

    // ZIP 上传需先解压和校验，同步 SDK 调用完成前临时文件不能删除。
    private PublishedSite publishZipSite(String bucketName, String appSubDomain, MultipartFile appFile) {
        Path tempDir;
        try {
            tempDir = Files.createTempDirectory("onlikee-site-publish-");
        } catch (IOException exception) {
            throw new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
        }

        try {
            List<ObjectClient.UploadItem> items = prepareZipUploadItems(appFile, tempDir);
            SiteClient.PublishRequest request = SiteClient.PublishRequest.builder(
                            bucketName,
                            List.of(UrlUtils.buildAppDomain(appSubDomain)),
                            items)
                    .parentPrefix(appSubDomain)
                    .enabled(true)
                    .indexDocument(INDEX_DOCUMENT)
                    .errorDocument("")
                    .spaFallback(true)
                    .build();

            SiteClient.PublishResult result = lightOssClient.sites().publish(request).data();
            return toPublishedSite(result.site());
        } catch (BizException exception) {
            throw exception;
        } catch (LightOssApiException exception) {
            throw translateApiException(exception, true);
        } catch (LightOssValidationException exception) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, exception.getMessage());
        } catch (LightOssException exception) {
            throw publishFailed(exception);
        } finally {
            deleteDirectoryQuietly(tempDir);
        }
    }

    private PublishedSite toPublishedSite(SiteClient.Site site) {
        String objectKey = buildObjectKey(site.rootPrefix(), site.indexDocument());
        return new PublishedSite(site.id(), site.bucket(), site.rootPrefix(), objectKey);
    }

    // 支持 zip 根目录直接放站点文件，或者外层只包含一个构建目录。
    private List<ObjectClient.UploadItem> prepareZipUploadItems(MultipartFile appFile, Path tempDir) {
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

            if (Files.isRegularFile(tempDir.resolve(INDEX_DOCUMENT))) {
                // 根目录直出的构建包放入统一 dist/ 顶层目录，满足 Light OSS 发布合同。
                return buildUploadItems(tempDir, regularFiles, "dist/");
            }

            List<Path> topLevelEntries = listTopLevelEntries(tempDir);
            if (topLevelEntries.size() != 1 || !Files.isDirectory(topLevelEntries.get(0))) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包结构无效");
            }

            Path topLevelDir = topLevelEntries.get(0);
            if (!Files.isRegularFile(topLevelDir.resolve(INDEX_DOCUMENT))) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包缺少 index.html");
            }
            return buildUploadItems(tempDir, regularFiles, "");
        } catch (BizException exception) {
            throw exception;
        } catch (IOException exception) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包无效");
        }
    }

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

        for (String segment : normalized.split("/")) {
            if (!StringUtils.hasText(segment) || ".".equals(segment) || "..".equals(segment)) {
                throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包包含非法路径");
            }
        }
        return normalized;
    }

    private Path resolveZipEntryPath(Path tempDir, String entryName) {
        Path targetPath = tempDir.resolve(entryName).normalize();
        if (!targetPath.startsWith(tempDir)) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "应用包包含非法路径");
        }
        return targetPath;
    }

    private List<Path> listRegularFiles(Path rootDir) throws IOException {
        try (Stream<Path> stream = Files.walk(rootDir)) {
            return stream.filter(Files::isRegularFile)
                    .filter(path -> !isIgnoredArchivePath(rootDir, path))
                    .sorted(Comparator.comparing(path -> rootDir.relativize(path).toString()))
                    .toList();
        }
    }

    private List<Path> listTopLevelEntries(Path rootDir) throws IOException {
        try (Stream<Path> stream = Files.list(rootDir)) {
            return stream.filter(path -> !isIgnoredArchivePath(rootDir, path))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .toList();
        }
    }

    private boolean isIgnoredArchivePath(Path rootDir, Path path) {
        Path relativePath = rootDir.relativize(path);
        if (relativePath.getNameCount() == 0) {
            return false;
        }

        for (Path segment : relativePath) {
            if ("__MACOSX".equals(segment.toString())) {
                return true;
            }
        }

        String fileName = path.getFileName().toString();
        return ".DS_Store".equals(fileName) || fileName.startsWith("._");
    }

    private List<ObjectClient.UploadItem> buildUploadItems(
            Path rootDir,
            List<Path> regularFiles,
            String prefix) {
        List<ObjectClient.UploadItem> items = new ArrayList<>(regularFiles.size());
        for (Path file : regularFiles) {
            String relativePath = rootDir.relativize(file).toString().replace('\\', '/');
            UploadSource source = UploadSource.fromPath(file, resolveContentType(detectContentType(file)));
            items.add(new ObjectClient.UploadItem(prefix + relativePath, source));
        }
        return items;
    }

    private void deleteSiteQuietly(long siteId) {
        try {
            lightOssClient.sites().delete(siteId);
        } catch (LightOssApiException exception) {
            if (exception.statusCode() != 404) {
                log.warn("light oss delete site failed: siteId={}, requestId={}, message={}",
                        siteId, requestId(exception), exception.serviceMessage());
            }
        } catch (LightOssException exception) {
            log.warn("light oss delete site failed: siteId={}, requestId={}", siteId, requestId(exception), exception);
        }
    }

    private void deleteObjectQuietly(String bucketName, String objectKey) {
        try {
            lightOssClient.objects().delete(bucketName, objectKey);
        } catch (LightOssApiException exception) {
            if (exception.statusCode() != 404) {
                log.warn("light oss delete object failed: bucket={}, objectKey={}, requestId={}, message={}",
                        bucketName, objectKey, requestId(exception), exception.serviceMessage());
            }
        } catch (LightOssException exception) {
            log.warn("light oss delete object failed: bucket={}, objectKey={}, requestId={}",
                    bucketName, objectKey, requestId(exception), exception);
        }
    }

    private void deleteFolderQuietly(String bucketName, String folderPath) {
        try {
            lightOssClient.explorer().deleteFolder(
                    new ExplorerClient.DeleteFolderRequest(bucketName, folderPath, true));
        } catch (LightOssApiException exception) {
            if (exception.statusCode() != 404) {
                log.warn("light oss delete folder failed: bucket={}, folderPath={}, requestId={}, message={}",
                        bucketName, folderPath, requestId(exception), exception.serviceMessage());
            }
        } catch (LightOssException exception) {
            log.warn("light oss delete folder failed: bucket={}, folderPath={}, requestId={}",
                    bucketName, folderPath, requestId(exception), exception);
        }
    }

    private BizException translateApiException(LightOssApiException exception, boolean packageValidationEnabled) {
        if ("domain_conflict".equals(exception.code())) {
            return new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        if (packageValidationEnabled
                && ("invalid_request".equals(exception.code())
                || "invalid_batch_manifest".equals(exception.code())
                || "batch_file_missing".equals(exception.code()))) {
            return new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID,
                    StringUtils.hasText(exception.serviceMessage())
                            ? exception.serviceMessage()
                            : ErrorCode.APPLICATION_PACKAGE_INVALID.getMessage());
        }
        return publishFailed(exception);
    }

    private BizException publishFailed(LightOssException exception) {
        if (exception instanceof LightOssApiException apiException
                && StringUtils.hasText(apiException.serviceMessage())) {
            return new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED, apiException.serviceMessage());
        }
        return new BizException(ErrorCode.APPLICATION_PUBLISH_FAILED);
    }

    private String requireFilename(MultipartFile appFile) {
        String originalFilename = appFile.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new BizException(ErrorCode.APPLICATION_PACKAGE_INVALID, "上传文件缺少文件名");
        }
        return originalFilename;
    }

    private String buildObjectKey(String rootPrefix, String indexDocument) {
        if (!StringUtils.hasText(indexDocument)) {
            return null;
        }
        if (!StringUtils.hasText(rootPrefix)) {
            return indexDocument;
        }
        return rootPrefix + indexDocument;
    }

    private String detectContentType(Path filePath) {
        try {
            return Files.probeContentType(filePath);
        } catch (IOException exception) {
            return null;
        }
    }

    private String resolveContentType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        try {
            return MediaType.parseMediaType(contentType).toString();
        } catch (IllegalArgumentException exception) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

    private String requestId(LightOssException exception) {
        return exception.requestId().orElse("unknown");
    }

    private void deleteDirectoryQuietly(Path directory) {
        if (directory == null || !Files.exists(directory)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(directory)) {
            stream.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException exception) {
                    log.warn("delete temp directory failed: path={}", path, exception);
                }
            });
        } catch (IOException exception) {
            log.warn("delete temp directory failed: path={}", directory, exception);
        }
    }
}
