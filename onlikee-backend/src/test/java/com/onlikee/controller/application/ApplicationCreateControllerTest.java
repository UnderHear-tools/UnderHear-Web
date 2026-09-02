package com.onlikee.controller.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.onlikee.exception.GlobalExceptionHandler;
import com.onlikee.pojo.dto.request.ApplicationCreateCollectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateConnectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.response.ApplicationCreateCollectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateConnectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.entity.User;
import com.onlikee.security.SessionAuthService;
import com.onlikee.service.application.ApplicationCreateService;

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
        dore.setAppUrl("https://demo.onlikee.cn/");
        when(sessionAuthService.getCurrentUser("token")).thenReturn(user);
        when(applicationCreateService.applicationCreateNew(eq(user), any(ApplicationCreateNewDort.class))).thenReturn(dore);

        mockMvc.perform(multipart("/application/create/new")
                        .file(new MockMultipartFile("appFile", "index.html", "text/html", "<html></html>".getBytes()))
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("appUrl", "https://demo.onlikee.cn/")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("应用创建成功！"))
                .andExpect(jsonPath("$.data.appUrl").value("https://demo.onlikee.cn/"));

        ArgumentCaptor<ApplicationCreateNewDort> dortCaptor = ArgumentCaptor.forClass(ApplicationCreateNewDort.class);
        verify(applicationCreateService).applicationCreateNew(eq(user), dortCaptor.capture());
        verify(sessionAuthService).getCurrentUser("token");
        ApplicationCreateNewDort capturedDort = dortCaptor.getValue();
        assertEquals("html", capturedDort.getFramework());
        assertEquals("Demo", capturedDort.getAppName());
        assertEquals("https://demo.onlikee.cn/", capturedDort.getAppUrl());
    }

    @Test
    // JSON 参数完整时应成功接入已有网站并返回应用 URL。
    void applicationCreateConnectShouldReturnSuccessResponse() throws Exception {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateConnectDore dore = new ApplicationCreateConnectDore();
        dore.setAppUrl("https://www.demo.com");
        when(sessionAuthService.getCurrentUser("token")).thenReturn(user);
        when(applicationCreateService.applicationCreateConnect(eq(user), any(ApplicationCreateConnectDort.class))).thenReturn(dore);

        mockMvc.perform(post("/application/create/connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "appUrl": "https://www.demo.com",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("应用创建成功！"))
                .andExpect(jsonPath("$.data.appUrl").value("https://www.demo.com"));

        ArgumentCaptor<ApplicationCreateConnectDort> dortCaptor = ArgumentCaptor.forClass(ApplicationCreateConnectDort.class);
        verify(applicationCreateService).applicationCreateConnect(eq(user), dortCaptor.capture());
        verify(sessionAuthService).getCurrentUser("token");
        ApplicationCreateConnectDort capturedDort = dortCaptor.getValue();
        assertEquals("Demo", capturedDort.getAppName());
        assertEquals("https://www.demo.com", capturedDort.getAppUrl());
    }

    @Test
    // JSON 参数完整时应成功收录网站并返回应用 URL。
    void applicationCreateCollectShouldReturnSuccessResponse() throws Exception {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateCollectDore dore = new ApplicationCreateCollectDore();
        dore.setAppUrl("https://www.demo.com");
        when(sessionAuthService.getCurrentUser("token")).thenReturn(user);
        when(applicationCreateService.applicationCreateCollect(eq(user), any(ApplicationCreateCollectDort.class))).thenReturn(dore);

        mockMvc.perform(post("/application/create/collect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "appUrl": "https://www.demo.com",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("应用收录成功！"))
                .andExpect(jsonPath("$.data.appUrl").value("https://www.demo.com"));

        ArgumentCaptor<ApplicationCreateCollectDort> dortCaptor = ArgumentCaptor.forClass(ApplicationCreateCollectDort.class);
        verify(applicationCreateService).applicationCreateCollect(eq(user), dortCaptor.capture());
        verify(sessionAuthService).getCurrentUser("token");
        ApplicationCreateCollectDort capturedDort = dortCaptor.getValue();
        assertEquals("Demo", capturedDort.getAppName());
        assertEquals("https://www.demo.com", capturedDort.getAppUrl());
    }

    @Test
    // 缺少上传文件时应触发参数校验失败。
    void applicationCreateNewShouldReturnValidationFailedWhenAppFileIsMissing() throws Exception {
        mockMvc.perform(multipart("/application/create/new")
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("appUrl", "https://demo.onlikee.cn/")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appFile不能为空"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateNew(any(), any());
    }

    @Test
    // 缺少必填文本字段时应触发参数校验失败。
    void applicationCreateNewShouldReturnValidationFailedWhenAppUrlIsMissing() throws Exception {
        mockMvc.perform(multipart("/application/create/new")
                        .file(new MockMultipartFile("appFile", "index.html", "text/html", "<html></html>".getBytes()))
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl不能为空"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateNew(any(), any());
    }

    @Test
    // 自建应用只允许 onlikee.cn 下的单标签 HTTPS 地址。
    void applicationCreateNewShouldReturnValidationFailedWhenAppUrlIsInvalid() throws Exception {
        mockMvc.perform(multipart("/application/create/new")
                        .file(new MockMultipartFile("appFile", "index.html", "text/html", "<html></html>".getBytes()))
                        .param("framework", "html")
                        .param("appName", "Demo")
                        .param("appUrl", "https://nested.demo.onlikee.cn/")
                        .param("visibility", "public")
                        .param("appDescription", "description")
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl格式无效"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateNew(any(), any());
    }

    @Test
    // Connect 缺少必填字段时应在进入登录态解析前触发参数校验失败。
    void applicationCreateConnectShouldReturnValidationFailedWhenRequiredFieldIsMissing() throws Exception {
        mockMvc.perform(post("/application/create/connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl不能为空"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateConnect(any(), any());
    }

    @Test
    // Connect 的 appUrl 必须是 HTTP(S) URL 或裸域名，非法值应在登录态解析前被拦截。
    void applicationCreateConnectShouldReturnValidationFailedWhenAppUrlIsInvalid() throws Exception {
        mockMvc.perform(post("/application/create/connect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "appUrl": "demo",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl格式无效"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateConnect(any(), any());
    }

    @Test
    // Collect 缺少必填字段时应在进入登录态解析前触发参数校验失败。
    void applicationCreateCollectShouldReturnValidationFailedWhenRequiredFieldIsMissing() throws Exception {
        mockMvc.perform(post("/application/create/collect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl不能为空"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateCollect(any(), any());
    }

    @Test
    // Collect 的 appUrl 必须是 HTTP(S) URL 或裸域名，非法值应在登录态解析前被拦截。
    void applicationCreateCollectShouldReturnValidationFailedWhenAppUrlIsInvalid() throws Exception {
        mockMvc.perform(post("/application/create/collect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "appName": "Demo",
                                  "appUrl": "demo",
                                  "visibility": "public",
                                  "appDescription": "description"
                                }
                                """)
                        .cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message").value("appUrl格式无效"));

        verify(sessionAuthService, never()).getCurrentUser(any());
        verify(applicationCreateService, never()).applicationCreateCollect(any(), any());
    }
}
