package com.underhear.controller.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.underhear.exception.GlobalExceptionHandler;
import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.entity.User;
import com.underhear.security.SessionAuthService;
import com.underhear.service.application.ApplicationCreateService;

import jakarta.servlet.http.Cookie;

@WebMvcTest(ApplicationCreateController.class)
@Import(GlobalExceptionHandler.class)
class ApplicationCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationCreateService applicationCreateService;

    @MockitoBean
    private SessionAuthService sessionAuthService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    // multipart 参数完整时应成功创建应用并返回 URL。
    void applicationCreateNewShouldReturnSuccessResponse() throws Exception {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateNewDore dore = new ApplicationCreateNewDore();
        dore.setAppUrl("https://demo.underhear.cn/");
        when(sessionAuthService.getCurrentUser("token")).thenReturn(user);
        when(applicationCreateService.applicationCreateNew(eq(user), any(ApplicationCreateNewDort.class))).thenReturn(dore);

        mockMvc.perform(multipart("/application/create/new")
                        .file(new MockMultipartFile("appFile", "index.html", "text/html", "<html></html>".getBytes()))
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("appEnglishName", "demo")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("应用创建成功！"))
                .andExpect(jsonPath("$.data.appUrl").value("https://demo.underhear.cn/"));

        ArgumentCaptor<ApplicationCreateNewDort> dortCaptor = ArgumentCaptor.forClass(ApplicationCreateNewDort.class);
        verify(applicationCreateService).applicationCreateNew(eq(user), dortCaptor.capture());
        verify(sessionAuthService).getCurrentUser("token");
        ApplicationCreateNewDort capturedDort = dortCaptor.getValue();
        assertEquals("html", capturedDort.getFramework());
        assertEquals("Demo", capturedDort.getAppName());
        assertEquals("demo", capturedDort.getAppEnglishName());
    }

    @Test
    // 缺少上传文件时应触发参数校验失败。
    void applicationCreateNewShouldReturnValidationFailedWhenAppFileIsMissing() throws Exception {
        mockMvc.perform(multipart("/application/create/new")
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("appEnglishName", "demo")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("参数校验失败"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateNew(any(), any());
    }

    @Test
    // 缺少必填文本字段时应触发参数校验失败。
    void applicationCreateNewShouldReturnValidationFailedWhenRequiredFieldIsMissing() throws Exception {
        mockMvc.perform(multipart("/application/create/new")
                        .file(new MockMultipartFile("appFile", "index.html", "text/html", "<html></html>".getBytes()))
                        .param("framework", "html")
                        .param("appEnglishName", "demo")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("参数校验失败"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateNew(any(), any());
    }
}
