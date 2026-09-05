package com.onlikee.module.application.service;

import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mock.web.MockMultipartFile;

import com.onlikee.module.application.util.ApplicationUrlUtils;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.module.application.mapper.ApplicationCreateMapper;
import com.onlikee.module.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.module.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.module.application.model.entity.ApplicationCollectEntity;
import com.onlikee.module.application.model.entity.ApplicationConnectEntity;
import com.onlikee.module.application.model.entity.ApplicationNewEntity;
import com.onlikee.module.user.model.entity.UserEntity;
import com.onlikee.module.application.service.ApplicationSitePublishService.PublishedSite;

@ExtendWith(MockitoExtension.class)
class ApplicationCreateServiceTest {

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

    @Mock
    private ApplicationCreateMapper applicationCreateMapper;

    @Mock
    private ApplicationSitePublishService applicationSitePublishService;

    @InjectMocks
    private ApplicationCreateService applicationCreateService;

    @Test
    // 应用地址已存在时不应继续发布站点。
    void applicationCreateNewShouldThrowWhenAppUrlAlreadyExists() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(1);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // HTML 框架信息应正常入库，发布服务只接收统一的 ZIP 应用包。
    void applicationCreateNewShouldPublishZipWithoutPassingFramework() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class))).thenReturn(1);

        ApplicationCreateNewVO result = applicationCreateService.applicationCreateNew(user, request);

        ArgumentCaptor<ApplicationNewEntity> applicationCaptor = ArgumentCaptor.forClass(ApplicationNewEntity.class);
        verify(applicationCreateMapper).insertApplicationNew(applicationCaptor.capture());
        verify(applicationSitePublishService).publish(eq("user-1"), eq("demo"), any());
        verify(applicationSitePublishService, never()).cleanupPublishedSite(any());
        assertEquals("https://demo.onlikee.com/", result.getAppUrl());
        assertEquals("demo", applicationCaptor.getValue().getAppSubDomain());
    }

    @Test
    // 其他框架应使用相同的 ZIP 发布入口。
    void applicationCreateNewShouldPublishZipForOtherFrameworks() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = zipRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo-zip")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo-zip"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class))).thenReturn(1);

        ApplicationCreateNewVO result = applicationCreateService.applicationCreateNew(user, request);

        verify(applicationSitePublishService).publish(eq("user-1"), eq("demo-zip"), any());
        assertEquals("https://demo-zip.onlikee.com/", result.getAppUrl());
    }

    @Test
    // 落库影响行数异常时应执行已发布站点的补偿清理。
    void applicationCreateNewShouldCleanupSiteWhenInsertReturnsUnexpectedRows() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class))).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APPLICATION_CREATE_FAILED.getCode(), exception.getCode());
        verify(applicationSitePublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 唯一键冲突时应翻译成应用地址重复，并清理已发布站点。
    void applicationCreateNewShouldCleanupSiteWhenInsertThrowsDuplicateKeyException() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class)))
                .thenThrow(new DuplicateKeyException("duplicate"));

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(applicationSitePublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 其他运行时异常应原样抛出，并清理已发布站点。
    void applicationCreateNewShouldCleanupSiteWhenInsertThrowsRuntimeException() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class)))
                .thenThrow(new IllegalStateException("boom"));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals("boom", exception.getMessage());
        verify(applicationSitePublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 正常创建时应按先建 bucket、再发布、最后落库的顺序执行。
    void applicationCreateNewShouldCallCollaboratorsInExpectedOrder() {
        UserEntity user = user();
        ApplicationCreateNewDTO request = htmlRequest();
        PublishedSite publishedSite = publishedSite();
        when(applicationCreateMapper.countNewByAppSubDomain("demo")).thenReturn(0);
        when(applicationSitePublishService.publish(eq("user-1"), eq("demo"), any()))
                .thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplicationNew(any(ApplicationNewEntity.class))).thenReturn(1);

        applicationCreateService.applicationCreateNew(user, request);

        InOrder inOrder = inOrder(applicationSitePublishService, applicationCreateMapper);
        inOrder.verify(applicationSitePublishService).publish(eq("user-1"), eq("demo"), any());
        inOrder.verify(applicationCreateMapper).insertApplicationNew(any(ApplicationNewEntity.class));
    }

    @Test
    // 接入已有网站时应规范化 URL 后落库，不发布 Light OSS 站点。
    void applicationCreateConnectShouldNormalizeUrlWithoutLightOss() {
        UserEntity user = user();
        ApplicationCreateConnectDTO request = connectRequest();
        when(applicationCreateMapper.countConnectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationConnect(any(ApplicationConnectEntity.class))).thenReturn(1);

        ApplicationCreateConnectVO result = applicationCreateService.applicationCreateConnect(user, request);

        ArgumentCaptor<ApplicationConnectEntity> applicationCaptor = ArgumentCaptor.forClass(ApplicationConnectEntity.class);
        verify(applicationCreateMapper).insertApplicationConnect(applicationCaptor.capture());
        verifyNoInteractions(applicationSitePublishService);
        ApplicationConnectEntity application = applicationCaptor.getValue();
        assertEquals("https://www.demo.com", result.getAppUrl());
        assertEquals("https://www.demo.com", application.getAppUrl());
    }

    @Test
    // 接入已有网站时 URL 已存在不应继续落库。
    void applicationCreateConnectShouldThrowWhenAppUrlAlreadyExists() {
        UserEntity user = user();
        ApplicationCreateConnectDTO request = connectRequest();
        when(applicationCreateMapper.countConnectByAppUrl("https://www.demo.com")).thenReturn(1);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateConnect(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(applicationCreateMapper, never()).insertApplicationConnect(any());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // 接入已有网站时唯一键冲突应翻译成应用地址重复。
    void applicationCreateConnectShouldThrowWhenInsertThrowsDuplicateKeyException() {
        UserEntity user = user();
        ApplicationCreateConnectDTO request = connectRequest();
        when(applicationCreateMapper.countConnectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationConnect(any(ApplicationConnectEntity.class)))
                .thenThrow(new DuplicateKeyException("duplicate"));

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateConnect(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // 接入已有网站时落库影响行数异常应返回创建失败。
    void applicationCreateConnectShouldThrowWhenInsertReturnsUnexpectedRows() {
        UserEntity user = user();
        ApplicationCreateConnectDTO request = connectRequest();
        when(applicationCreateMapper.countConnectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationConnect(any(ApplicationConnectEntity.class))).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateConnect(user, request));

        assertEquals(ErrorCode.APPLICATION_CREATE_FAILED.getCode(), exception.getCode());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // 收录网站时应规范化 URL 后落库，不发布 Light OSS 站点。
    void applicationCreateCollectShouldNormalizeUrlWithoutLightOss() {
        UserEntity user = user();
        ApplicationCreateCollectDTO request = collectRequest();
        when(applicationCreateMapper.countCollectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationCollect(any(ApplicationCollectEntity.class))).thenReturn(1);

        ApplicationCreateCollectVO result = applicationCreateService.applicationCreateCollect(user, request);

        ArgumentCaptor<ApplicationCollectEntity> applicationCaptor = ArgumentCaptor.forClass(ApplicationCollectEntity.class);
        verify(applicationCreateMapper).insertApplicationCollect(applicationCaptor.capture());
        verifyNoInteractions(applicationSitePublishService);
        ApplicationCollectEntity application = applicationCaptor.getValue();
        assertEquals("https://www.demo.com", result.getAppUrl());
        assertEquals("https://www.demo.com", application.getAppUrl());
    }

    @Test
    // 收录网站时 URL 已存在不应继续落库。
    void applicationCreateCollectShouldThrowWhenAppUrlAlreadyExists() {
        UserEntity user = user();
        ApplicationCreateCollectDTO request = collectRequest();
        when(applicationCreateMapper.countCollectByAppUrl("https://www.demo.com")).thenReturn(1);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateCollect(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(applicationCreateMapper, never()).insertApplicationCollect(any());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // 收录网站时唯一键冲突应翻译成应用地址重复。
    void applicationCreateCollectShouldThrowWhenInsertThrowsDuplicateKeyException() {
        UserEntity user = user();
        ApplicationCreateCollectDTO request = collectRequest();
        when(applicationCreateMapper.countCollectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationCollect(any(ApplicationCollectEntity.class)))
                .thenThrow(new DuplicateKeyException("duplicate"));

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateCollect(user, request));

        assertEquals(ErrorCode.APP_URL_ALREADY_EXISTS.getCode(), exception.getCode());
        verifyNoInteractions(applicationSitePublishService);
    }

    @Test
    // 收录网站时落库影响行数异常应返回创建失败。
    void applicationCreateCollectShouldThrowWhenInsertReturnsUnexpectedRows() {
        UserEntity user = user();
        ApplicationCreateCollectDTO request = collectRequest();
        when(applicationCreateMapper.countCollectByAppUrl("https://www.demo.com")).thenReturn(0);
        when(applicationCreateMapper.insertApplicationCollect(any(ApplicationCollectEntity.class))).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateCollect(user, request));

        assertEquals(ErrorCode.APPLICATION_CREATE_FAILED.getCode(), exception.getCode());
        verifyNoInteractions(applicationSitePublishService);
    }

    private UserEntity user() {
        UserEntity user = new UserEntity();
        user.setUuid("user-1");
        return user;
    }

    private ApplicationCreateNewDTO htmlRequest() {
        ApplicationCreateNewDTO request = new ApplicationCreateNewDTO();
        request.setFramework("html");
        request.setAppName("Demo");
        request.setAppSubDomain("demo");
        request.setVisibility("public");
        request.setAppDescription("description");
        request.setAppFile(new MockMultipartFile(
                "appFile",
                "dist.zip",
                "application/zip",
                "zip-content".getBytes()));
        return request;
    }

    private ApplicationCreateNewDTO zipRequest() {
        ApplicationCreateNewDTO request = new ApplicationCreateNewDTO();
        request.setFramework("vue");
        request.setAppName("Demo Zip");
        request.setAppSubDomain("demo-zip");
        request.setVisibility("public");
        request.setAppDescription("description");
        request.setAppFile(new MockMultipartFile(
                "appFile",
                "dist.zip",
                "application/zip",
                "zip-content".getBytes()));
        return request;
    }

    private ApplicationCreateConnectDTO connectRequest() {
        ApplicationCreateConnectDTO request = new ApplicationCreateConnectDTO();
        request.setAppName("Demo Website");
        request.setAppUrl("HTTPS://WWW.DEMO.COM");
        request.setVisibility("public");
        request.setAppDescription("description");
        return request;
    }

    private ApplicationCreateCollectDTO collectRequest() {
        ApplicationCreateCollectDTO request = new ApplicationCreateCollectDTO();
        request.setAppName("Demo Website");
        request.setAppUrl("HTTPS://WWW.DEMO.COM");
        request.setVisibility("public");
        request.setAppDescription("description");
        return request;
    }

    private PublishedSite publishedSite() {
        return new PublishedSite(1L, "user-1", "demo/dist/", "demo/dist/index.html");
    }
}
