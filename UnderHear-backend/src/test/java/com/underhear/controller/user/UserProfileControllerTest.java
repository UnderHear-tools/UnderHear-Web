package com.underhear.controller.user;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.exception.GlobalExceptionHandler;
import com.underhear.pojo.entity.User;
import com.underhear.security.SessionAuthService;
import com.underhear.service.user.UserProfileService;

@WebMvcTest(UserProfileController.class)
@Import(GlobalExceptionHandler.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserProfileService userProfileService;

    @MockitoBean
    private SessionAuthService sessionAuthService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    // 公开资料查询成功时应返回与 /auth/me 一致的用户信息结构。
    void profileShouldReturnPublicUserInfo() throws Exception {
        User user = user();
        when(userProfileService.getUserByNickname("tester")).thenReturn(user);

        mockMvc.perform(get("/users/tester"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.message").value("请求成功"))
                .andExpect(jsonPath("$.data.uuid").value("user-1"))
                .andExpect(jsonPath("$.data.nickname").value("tester"))
                .andExpect(jsonPath("$.data.email").value("tester@example.com"))
                .andExpect(jsonPath("$.data.avatarUrl").value("https://avatar/tester.png"));
    }

    @Test
    // 未携带 cookie 时也应走公开查询链路，不触发登录态解析。
    void profileShouldNotRequireAuthenticationCookie() throws Exception {
        when(userProfileService.getUserByNickname("tester")).thenReturn(user());

        mockMvc.perform(get("/users/tester"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"));

        verify(sessionAuthService, never()).getCurrentUser(null);
    }

    @Test
    // 用户不存在时应透出统一的 USER_NOT_FOUND 响应。
    void profileShouldReturnUserNotFoundWhenMissing() throws Exception {
        when(userProfileService.getUserByNickname("missing")).thenThrow(new BizException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(get("/users/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("未找到该用户"))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        user.setAvatarUrl("https://avatar/tester.png");
        return user;
    }
}
