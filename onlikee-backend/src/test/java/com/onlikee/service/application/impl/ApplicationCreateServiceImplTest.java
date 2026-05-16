package com.onlikee.service.application.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.mapper.application.ApplicationCreateMapper;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.request.LightOssPublishedSiteDort;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.entity.Application;
import com.onlikee.pojo.entity.User;
import com.onlikee.service.lightoss.LightOssPublishService;

@ExtendWith(MockitoExtension.class)
class ApplicationCreateServiceImplTest {

    @Mock
    private ApplicationCreateMapper applicationCreateMapper;

    @Mock
    private LightOssPublishService lightOssPublishService;

    @InjectMocks
    private ApplicationCreateServiceImpl applicationCreateService;

    @Test
    // 英文名已存在时不应继续发布站点。
    void applicationCreateNewShouldThrowWhenEnglishNameAlreadyExists() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(1);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(lightOssPublishService, never()).ensureBucketExists(any());
    }

    @Test
    // HTML 应用创建成功时应走单文件发布分支。
    void applicationCreateNewShouldPublishHtmlWhenFrameworkIsHtml() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(0);
        when(lightOssPublishService.publishHtml(eq("user-1"), eq("demo"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class))).thenReturn(1);

        ApplicationCreateNewDore result = applicationCreateService.applicationCreateNew(user, request);

        ArgumentCaptor<Application> applicationCaptor = ArgumentCaptor.forClass(Application.class);
        verify(applicationCreateMapper).insertApplication(applicationCaptor.capture());
        verify(lightOssPublishService).publishHtml(eq("user-1"), eq("demo"), any());
        verify(lightOssPublishService, never()).publishZipSite(eq("user-1"), eq("demo"), any());
        verify(lightOssPublishService, never()).cleanupPublishedSite(any());
        assertEquals("https://demo.onlikee.cn/", result.getAppUrl());
        assertEquals("demo", applicationCaptor.getValue().getAppEnglishName());
    }

    @Test
    // 非 HTML 应用创建成功时应走 ZIP 发布分支。
    void applicationCreateNewShouldPublishZipSiteWhenFrameworkIsNotHtml() {
        User user = user();
        ApplicationCreateNewDort request = zipRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo-zip")).thenReturn(0);
        when(lightOssPublishService.publishZipSite(eq("user-1"), eq("demo-zip"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class))).thenReturn(1);

        ApplicationCreateNewDore result = applicationCreateService.applicationCreateNew(user, request);

        verify(lightOssPublishService).publishZipSite(eq("user-1"), eq("demo-zip"), any());
        verify(lightOssPublishService, never()).publishHtml(eq("user-1"), eq("demo-zip"), any());
        assertEquals("https://demo-zip.onlikee.cn/", result.getAppUrl());
    }

    @Test
    // 落库影响行数异常时应执行已发布站点的补偿清理。
    void applicationCreateNewShouldCleanupSiteWhenInsertReturnsUnexpectedRows() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(0);
        when(lightOssPublishService.publishHtml(eq("user-1"), eq("demo"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class))).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APPLICATION_CREATE_FAILED.getCode(), exception.getCode());
        verify(lightOssPublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 唯一键冲突时应翻译成英文名重复，并清理已发布站点。
    void applicationCreateNewShouldCleanupSiteWhenInsertThrowsDuplicateKeyException() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(0);
        when(lightOssPublishService.publishHtml(eq("user-1"), eq("demo"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class)))
                .thenThrow(new DuplicateKeyException("duplicate"));

        BizException exception = assertThrows(
                BizException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(lightOssPublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 其他运行时异常应原样抛出，并清理已发布站点。
    void applicationCreateNewShouldCleanupSiteWhenInsertThrowsRuntimeException() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(0);
        when(lightOssPublishService.publishHtml(eq("user-1"), eq("demo"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class)))
                .thenThrow(new IllegalStateException("boom"));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> applicationCreateService.applicationCreateNew(user, request));

        assertEquals("boom", exception.getMessage());
        verify(lightOssPublishService).cleanupPublishedSite(publishedSite);
    }

    @Test
    // 正常创建时应按先建 bucket、再发布、最后落库的顺序执行。
    void applicationCreateNewShouldCallCollaboratorsInExpectedOrder() {
        User user = user();
        ApplicationCreateNewDort request = htmlRequest();
        LightOssPublishedSiteDort publishedSite = publishedSite();
        when(applicationCreateMapper.countByAppEnglishName("demo")).thenReturn(0);
        when(lightOssPublishService.publishHtml(eq("user-1"), eq("demo"), any())).thenReturn(publishedSite);
        when(applicationCreateMapper.insertApplication(any(Application.class))).thenReturn(1);

        applicationCreateService.applicationCreateNew(user, request);

        InOrder inOrder = inOrder(lightOssPublishService, applicationCreateMapper);
        inOrder.verify(lightOssPublishService).ensureBucketExists("user-1");
        inOrder.verify(lightOssPublishService).publishHtml(eq("user-1"), eq("demo"), any());
        inOrder.verify(applicationCreateMapper).insertApplication(any(Application.class));
    }

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        return user;
    }

    private ApplicationCreateNewDort htmlRequest() {
        ApplicationCreateNewDort request = new ApplicationCreateNewDort();
        request.setFramework("html");
        request.setAppName("Demo");
        request.setAppEnglishName("demo");
        request.setVisibility("public");
        request.setAppDescription("description");
        request.setAppFile(new MockMultipartFile(
                "appFile",
                "index.html",
                "text/html",
                "<html></html>".getBytes()));
        return request;
    }

    private ApplicationCreateNewDort zipRequest() {
        ApplicationCreateNewDort request = new ApplicationCreateNewDort();
        request.setFramework("vue");
        request.setAppName("Demo Zip");
        request.setAppEnglishName("demo-zip");
        request.setVisibility("public");
        request.setAppDescription("description");
        request.setAppFile(new MockMultipartFile(
                "appFile",
                "dist.zip",
                "application/zip",
                "zip-content".getBytes()));
        return request;
    }

    private LightOssPublishedSiteDort publishedSite() {
        LightOssPublishedSiteDort publishedSite = new LightOssPublishedSiteDort();
        publishedSite.setSiteId(1L);
        publishedSite.setBucketName("user-1");
        publishedSite.setRootPrefix("demo/");
        return publishedSite;
    }
}
