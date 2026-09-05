package com.onlikee.auth.oauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.fastjson.JSONObject;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.auth.oauth.mapper.AuthGiteeMapper;
import com.onlikee.auth.oauth.model.dto.request.UserGiteeDort;
import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackWithTokenDore;
import com.onlikee.auth.oauth.model.dto.response.OAuthPendingSignupDore;
import com.onlikee.user.model.entity.User;
import com.onlikee.auth.oauth.model.entity.UserGitee;
import com.onlikee.auth.service.JwtTokenService;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.auth.oauth.service.OAuthSignupService;
import com.onlikee.user.service.UserService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@ExtendWith(MockitoExtension.class)
class AuthGiteeServiceImplTest {

    @Mock
    private AuthGiteeMapper authGiteeMapper;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private SessionAuthService sessionAuthService;

    @Mock
    private OAuthSignupService oauthSignupService;

    @InjectMocks
    private AuthGiteeServiceImpl authGiteeService;

    @Test
    // 授权结果无效时应直接按授权失败处理。
    void loginShouldThrowWhenAuthorizationFails() {
        BizException exception = assertThrows(BizException.class, () -> authGiteeService.login(null));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
    }

    @Test
    // 第一次登录时只应创建 pending signup，不应直接创建完整用户。
    void loginShouldReturnPendingSignupWhenGiteeAccountDoesNotExist() {
        AuthResponse<AuthUser> authResponse = successGiteeResponse();
        when(authGiteeMapper.countByGiteeId(2002L)).thenReturn(0);
        when(oauthSignupService.createGiteePendingSignup(any(UserGiteeDort.class))).thenReturn(pendingSignup());

        OAuthCallbackWithTokenDore result = authGiteeService.login(authResponse);

        verify(oauthSignupService).createGiteePendingSignup(any(UserGiteeDort.class));
        verify(authGiteeMapper, never()).saveUserGiteeAndUser(any(), any());
        verify(jwtTokenService, never()).generateToken(any());
        verify(sessionAuthService, never()).whitelistToken(any());
        assertEquals(OAuthCallbackWithTokenDore.SIGNUP_REQUIRED, result.getStatus());
        assertEquals("pending-token", result.getPendingSignupToken());
        assertEquals("gitee", result.getProvider());
        assertEquals("gitee-user", result.getSuggestedNickname());
    }

    @Test
    // 已存在用户再次登录时应更新第三方资料并刷新登录态。
    void loginShouldUpdateExistingUserWhenGiteeAccountExists() {
        AuthResponse<AuthUser> authResponse = successGiteeResponse();
        User existingUser = existingUser();
        when(authGiteeMapper.countByGiteeId(2002L)).thenReturn(1);
        when(userService.getUserByGiteeId(2002L)).thenReturn(existingUser);
        when(jwtTokenService.generateToken("user-1")).thenReturn("jwt-token");

        OAuthCallbackWithTokenDore result = authGiteeService.login(authResponse);

        verify(authGiteeMapper).updateUserGiteeByGiteeId(any(UserGitee.class));
        verify(userService).updateUserLastLoginByUuid(eq("user-1"), any(), eq("GITEE_OAUTH"));
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord("user-1", "GITEE_OAUTH");
        assertEquals(OAuthCallbackWithTokenDore.LOGIN_SUCCESS, result.getStatus());
        assertEquals("jwt-token", result.getToken());
        assertEquals("GITEE_OAUTH", result.getLoginSource());
        assertEquals("existing-user", result.getUserInfo().getNickname());
    }

    @Test
    // 授权成功但缺少 Gitee ID 时也应拒绝后续登录流程。
    void loginShouldThrowWhenGiteeIdIsMissing() {
        AuthResponse<AuthUser> authResponse = successGiteeResponse();
        authResponse.getData().getRawUserInfo().remove("id");

        BizException exception = assertThrows(BizException.class, () -> authGiteeService.login(authResponse));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
        verify(authGiteeMapper, never()).saveUserGiteeAndUser(any(), any());
        verify(oauthSignupService, never()).createGiteePendingSignup(any());
    }

    @Test
    // exists 对空入参应直接返回 false。
    void existsShouldReturnFalseWhenInputIsNull() {
        assertEquals(false, authGiteeService.exists(null));
    }

    @Test
    // exists 应根据 mapper 查询结果判断第三方账号是否存在。
    void existsShouldReturnMapperResultWhenGiteeIdIsPresent() {
        UserGiteeDort dort = new UserGiteeDort();
        dort.setGiteeId(2002L);
        when(authGiteeMapper.countByGiteeId(2002L)).thenReturn(1);

        boolean exists = authGiteeService.exists(dort);

        assertEquals(true, exists);
    }

    private AuthResponse<AuthUser> successGiteeResponse() {
        AuthToken token = new AuthToken();
        token.setAccessToken("gitee-token");

        JSONObject rawUserInfo = new JSONObject();
        rawUserInfo.put("id", 2002L);
        rawUserInfo.put("name", "gitee-user");
        rawUserInfo.put("avatar_url", "https://avatar/gitee.png");
        rawUserInfo.put("email", "gitee@example.com");
        rawUserInfo.put("bio", "gitee bio");
        rawUserInfo.put("html_url", "https://gitee.com/demo");

        AuthUser authUser = new AuthUser();
        authUser.setToken(token);
        authUser.setRawUserInfo(rawUserInfo);

        return new AuthResponse<>(2000, "ok", authUser);
    }

    private User existingUser() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("existing-user");
        user.setEmail("existing@example.com");
        user.setAvatarUrl("https://avatar/existing.png");
        user.setLastLoginSource("LEGACY");
        return user;
    }

    private OAuthPendingSignupDore pendingSignup() {
        OAuthPendingSignupDore pendingSignup = new OAuthPendingSignupDore();
        pendingSignup.setPendingSignupToken("pending-token");
        pendingSignup.setProvider("gitee");
        pendingSignup.setAvatarUrl("https://avatar/gitee.png");
        pendingSignup.setSuggestedNickname("gitee-user");
        pendingSignup.setEmail("gitee@example.com");
        return pendingSignup;
    }
}
