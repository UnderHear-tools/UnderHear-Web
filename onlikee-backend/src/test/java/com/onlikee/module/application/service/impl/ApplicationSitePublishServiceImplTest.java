package com.onlikee.module.application.service.impl;

import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.onlikee.module.application.util.ApplicationUrlUtils;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.lightoss.BucketClient;
import com.onlikee.lightoss.ExplorerClient;
import com.onlikee.lightoss.LightOssClient;
import com.onlikee.lightoss.LightOssResponse;
import com.onlikee.lightoss.ObjectClient;
import com.onlikee.lightoss.SiteClient;
import com.onlikee.lightoss.exception.LightOssApiException;
import com.onlikee.lightoss.exception.LightOssTransportException;
import com.onlikee.module.application.service.ApplicationSitePublishService.PublishedSite;

@ExtendWith(MockitoExtension.class)
class ApplicationSitePublishServiceImplTest {

    private Object previousAppDomainSuffix;

    @BeforeEach
    // 单元测试显式准备域名并恢复静态状态，避免依赖执行顺序。
    void configureAppDomain() {
        previousAppDomainSuffix = ReflectionTestUtils.getField(ApplicationUrlUtils.class, "appDomainSuffix");
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", ".onlikee.com");
    }

    @AfterEach
    void restoreAppDomain() {
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", previousAppDomainSuffix);
    }

    private static final Instant TIME = Instant.parse("2026-01-01T00:00:00Z");

    @Mock
    private LightOssClient lightOssClient;

    @Mock
    private BucketClient bucketClient;

    @Mock
    private SiteClient siteClient;

    @Mock
    private ExplorerClient explorerClient;

    @Mock
    private ObjectClient objectClient;

    private ApplicationSitePublishServiceImpl publishService;

    @BeforeEach
    void setUp() {
        publishService = new ApplicationSitePublishServiceImpl(lightOssClient);
    }

    @Test
    // bucket_exists 是创建应用时的幂等成功，不应阻断后续发布。
    void publishShouldContinueWhenBucketAlreadyExists() throws Exception {
        usePublishClients();
        when(bucketClient.create("user-1"))
                .thenThrow(apiException(409, "bucket_exists", "bucket already exists"));
        when(siteClient.publish(any())).thenReturn(response(
                new SiteClient.PublishResult(1, site("demo/dist/", "index.html"))));

        publishService.publish("user-1", "demo", zipFile(Map.of("index.html", "home")));

        verify(siteClient).publish(any());
    }

    @Test
    // zip 根目录直出时应增加 dist/ 顶层目录，并忽略 macOS 元数据。
    void publishShouldMapRootZipFilesUnderDistFolder() throws Exception {
        usePublishClients();
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("index.html", "<html lang=\"zh-CN\">中文内容</html>");
        entries.put("assets/app.js", "app");
        entries.put("assets/logo.svg", "<svg></svg>");
        entries.put("__MACOSX/._index.html", "metadata");
        when(siteClient.publish(any())).thenReturn(response(
                new SiteClient.PublishResult(2, site("demo/dist/", "index.html"))));

        PublishedSite publishedSite = publishService.publish("user-1", "demo", zipFile(entries));

        verify(bucketClient).create("user-1");
        ArgumentCaptor<SiteClient.PublishRequest> captor = ArgumentCaptor.forClass(SiteClient.PublishRequest.class);
        verify(siteClient).publish(captor.capture());
        SiteClient.PublishRequest request = captor.getValue();
        assertEquals("demo", request.parentPrefix());
        assertEquals(List.of("demo.onlikee.com"), request.domains());
        assertEquals(
                List.of("dist/assets/app.js", "dist/assets/logo.svg", "dist/index.html"),
                relativePaths(request.items()));
        ObjectClient.UploadItem indexItem = request.items().stream()
                .filter(item -> "dist/index.html".equals(item.relativePath()))
                .findFirst()
                .orElseThrow();
        assertEquals("text/html", indexItem.source().contentType());
        assertEquals(new PublishedSite(1L, "user-1", "demo/dist/", "demo/dist/index.html"), publishedSite);
    }

    @Test
    // 外层只有一个构建目录时应保留原始目录层级。
    void publishShouldPreserveSingleTopLevelZipFolder() throws Exception {
        usePublishClients();
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("build/index.html", "home");
        entries.put("build/app.js", "app");
        when(siteClient.publish(any())).thenReturn(response(
                new SiteClient.PublishResult(2, site("demo/build/", "index.html"))));

        publishService.publish("user-1", "demo", zipFile(entries));

        ArgumentCaptor<SiteClient.PublishRequest> captor = ArgumentCaptor.forClass(SiteClient.PublishRequest.class);
        verify(siteClient).publish(captor.capture());
        assertEquals(List.of("build/app.js", "build/index.html"), relativePaths(captor.getValue().items()));
    }

    @Test
    // 非 zip 文件或缺少 index.html 的包应在发起 SDK 发布请求前被拒绝。
    void publishShouldRejectInvalidZipPackage() throws Exception {
        when(lightOssClient.buckets()).thenReturn(bucketClient);
        BizException htmlFile = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", htmlFile()));
        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), htmlFile.getCode());

        BizException fakeZip = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", new MockMultipartFile(
                        "appFile", "site.zip", "application/zip", "not-a-zip".getBytes(StandardCharsets.UTF_8))));
        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), fakeZip.getCode());

        BizException emptyZip = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", zipFile(Map.of())));
        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), emptyZip.getCode());

        BizException missingIndex = assertThrows(
                BizException.class,
                () -> publishService.publish(
                        "user-1",
                        "demo",
                        zipFile(Map.of("assets/app.js", "app"))));
        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), missingIndex.getCode());
        verifyNoInteractions(siteClient);
    }

    @Test
    // zip entry 不得通过 .. 越出临时目录。
    void publishShouldRejectZipSlipEntry() throws Exception {
        when(lightOssClient.buckets()).thenReturn(bucketClient);
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("../outside.txt", "outside");
        entries.put("index.html", "home");

        BizException exception = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", zipFile(entries)));

        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), exception.getCode());
        verifyNoInteractions(siteClient);
    }

    @Test
    // 域名冲突应翻译成 onlikee 的应用地址重复错误。
    void publishShouldTranslateDomainConflict() throws Exception {
        usePublishClients();
        when(siteClient.publish(any()))
                .thenThrow(apiException(409, "domain_conflict", "domain already exists"));

        BizException exception = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", zipFile(Map.of("index.html", "home"))));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
    }

    @Test
    // Light OSS 的 ZIP manifest 错误应保留服务端说明并映射成应用包无效。
    void publishShouldTranslateZipManifestError() throws Exception {
        usePublishClients();
        when(siteClient.publish(any()))
                .thenThrow(apiException(400, "invalid_batch_manifest", "manifest is invalid"));

        BizException exception = assertThrows(
                BizException.class,
                () -> publishService.publish(
                        "user-1",
                        "demo",
                        zipFile(Map.of("index.html", "home"))));

        assertEquals(ErrorCode.APPLICATION_PACKAGE_INVALID.getCode(), exception.getCode());
        assertEquals("manifest is invalid", exception.getMessage());
    }

    @Test
    // 传输故障不应泄漏 SDK 异常，对外统一为应用发布失败。
    void publishShouldTranslateTransportFailure() throws Exception {
        usePublishClients();
        when(siteClient.publish(any()))
                .thenThrow(new LightOssTransportException("network failed", "request-1", null));

        BizException exception = assertThrows(
                BizException.class,
                () -> publishService.publish("user-1", "demo", zipFile(Map.of("index.html", "home"))));

        assertEquals(ErrorCode.APPLICATION_PUBLISH_FAILED.getCode(), exception.getCode());
    }

    @Test
    // 有 rootPrefix 的站点应在删除站点后递归删除整个目录。
    void cleanupPublishedSiteShouldDeleteSiteAndFolder() {
        when(lightOssClient.sites()).thenReturn(siteClient);
        when(lightOssClient.explorer()).thenReturn(explorerClient);
        PublishedSite publishedSite = new PublishedSite(
                1L, "user-1", "demo/dist/", "demo/dist/index.html");

        publishService.cleanupPublishedSite(publishedSite);

        verify(siteClient).delete(1L);
        ArgumentCaptor<ExplorerClient.DeleteFolderRequest> captor =
                ArgumentCaptor.forClass(ExplorerClient.DeleteFolderRequest.class);
        verify(explorerClient).deleteFolder(captor.capture());
        assertEquals("user-1", captor.getValue().bucket());
        assertEquals("demo/dist/", captor.getValue().path());
        assertTrue(captor.getValue().recursive());
        verify(objectClient, never()).delete(any(), any());
    }

    @Test
    // 站点删除失败时仍应继续执行目录补偿。
    void cleanupPublishedSiteShouldContinueWhenSiteDeletionFails() {
        when(lightOssClient.sites()).thenReturn(siteClient);
        when(lightOssClient.explorer()).thenReturn(explorerClient);
        when(siteClient.delete(1L)).thenThrow(apiException(500, "internal_error", "delete failed"));
        PublishedSite publishedSite = new PublishedSite(
                1L, "user-1", "demo/dist/", "demo/dist/index.html");

        publishService.cleanupPublishedSite(publishedSite);

        verify(explorerClient).deleteFolder(new ExplorerClient.DeleteFolderRequest(
                "user-1", "demo/dist/", true));
    }

    @Test
    // 站点已不存在时仍应继续清理根目录对象。
    void cleanupPublishedSiteShouldIgnoreMissingSiteAndDeleteRootObject() {
        when(lightOssClient.sites()).thenReturn(siteClient);
        when(lightOssClient.objects()).thenReturn(objectClient);
        when(siteClient.delete(1L)).thenThrow(apiException(404, "site_not_found", "site not found"));
        PublishedSite publishedSite = new PublishedSite(1L, "user-1", "", "index.html");

        publishService.cleanupPublishedSite(publishedSite);

        verify(objectClient).delete("user-1", "index.html");
        verifyNoInteractions(explorerClient);
    }

    private MockMultipartFile htmlFile() {
        return new MockMultipartFile(
                "appFile", "index.html", "text/html", "<html></html>".getBytes(StandardCharsets.UTF_8));
    }

    private void usePublishClients() {
        when(lightOssClient.buckets()).thenReturn(bucketClient);
        when(lightOssClient.sites()).thenReturn(siteClient);
    }

    private MockMultipartFile zipFile(Map<String, String> entries) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutput = new ZipOutputStream(output, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, String> entry : entries.entrySet()) {
                zipOutput.putNextEntry(new ZipEntry(entry.getKey()));
                zipOutput.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zipOutput.closeEntry();
            }
        }
        return new MockMultipartFile("appFile", "site.zip", "application/zip", output.toByteArray());
    }

    private List<String> relativePaths(List<ObjectClient.UploadItem> items) {
        return items.stream().map(ObjectClient.UploadItem::relativePath).toList();
    }

    private SiteClient.Site site(String rootPrefix, String indexDocument) {
        return new SiteClient.Site(
                1L,
                "user-1",
                rootPrefix,
                true,
                indexDocument,
                "",
                true,
                List.of("demo.onlikee.com"),
                TIME,
                TIME);
    }

    private LightOssApiException apiException(int status, String code, String message) {
        return new LightOssApiException(status, code, message, "request-1");
    }

    private <T> LightOssResponse<T> response(T data) {
        return new LightOssResponse<>(data, "request-1");
    }
}
