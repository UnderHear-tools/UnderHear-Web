package com.onlikee.controller.user;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.exception.GlobalExceptionHandler;
import com.onlikee.pojo.entity.User;
import com.onlikee.security.AuthCookieService;
import com.onlikee.security.SessionAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionAuthService sessionAuthService;

    @MockitoBean
    private AuthCookieService authCookieService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    // 查询当前用户成功时应返回统一响应结构。
    void meShouldReturnCurrentUserInfo() throws Exception {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        user.setAvatarUrl("https://avatar/tester.png");
        when(sessionAuthService.getCurrentUser("token")).thenReturn(user);

        mockMvc.perform(get("/auth/me").cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("请求成功"))
                .andExpect(jsonPath("$.data.uuid").value("user-1"))
                .andExpect(jsonPath("$.data.nickname").value("tester"))
                .andExpect(jsonPath("$.data.email").value("tester@example.com"))
                .andExpect(jsonPath("$.data.avatarUrl").value("https://avatar/tester.png"));
    }

    @Test
    // 未登录时仍应保持现有的 HTTP 200 契约。
    void meShouldReturnNotLoginResponseWithHttpOk() throws Exception {
        when(sessionAuthService.getCurrentUser("expired")).thenThrow(new BizException(ErrorCode.NOT_LOGIN));

        mockMvc.perform(get("/auth/me").cookie(new Cookie("auth_token", "expired")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("NOT_LOGIN"))
                .andExpect(jsonPath("$.message").value("未登录或登录已过期"))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

    @Test
    // 退出当前登录态时应调用 session 和 cookie 清理逻辑。
    void logoutShouldInvalidateCurrentTokenAndClearCookie() throws Exception {
        mockMvc.perform(post("/auth/logout").cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("请求成功"));

        verify(sessionAuthService).logout("token");
        verify(authCookieService).clearToken(any(HttpServletResponse.class));
    }

    @Test
    // 全部退出时应调用全量失效逻辑并清理 cookie。
    void logoutAllShouldInvalidateAllTokensAndClearCookie() throws Exception {
        mockMvc.perform(post("/auth/logout-all").cookie(new Cookie("auth_token", "token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("请求成功"));

        verify(sessionAuthService).logoutAll("token");
        verify(authCookieService).clearToken(any(HttpServletResponse.class));
    }
}
