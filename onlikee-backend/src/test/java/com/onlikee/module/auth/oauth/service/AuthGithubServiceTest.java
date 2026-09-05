package com.onlikee.module.auth.oauth.service;

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
import com.onlikee.module.auth.oauth.mapper.AuthGithubMapper;
import com.onlikee.module.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.module.user.model.entity.UserEntity;
import com.onlikee.module.auth.oauth.model.entity.UserGithubEntity;
import com.onlikee.module.auth.service.JwtTokenService;
import com.onlikee.module.auth.service.SessionAuthService;
import com.onlikee.module.user.service.UserService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@ExtendWith(MockitoExtension.class)
class AuthGithubServiceTest {

    @Mock
    private AuthGithubMapper authGithubMapper;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private SessionAuthService sessionAuthService;

    @Mock
    private OAuthSignupService oauthSignupService;

    @InjectMocks
    private AuthGithubService authGithubService;

    @Test
    // 授权结果无效时应直接按授权失败处理。
    void loginShouldThrowWhenAuthorizationFails() {
        BizException exception = assertThrows(BizException.class, () -> authGithubService.login(null));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
    }

    @Test
    // 第一次登录时只应创建 pending signup，不应直接创建完整用户。
    void loginShouldReturnPendingSignupWhenGithubAccountDoesNotExist() {
        AuthResponse<AuthUser> authResponse = successGithubResponse();
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(0);
        when(oauthSignupService.createGithubPendingSignup(any(UserGithubDTO.class))).thenReturn(pendingSignup());

        OAuthCallbackWithTokenDTO result = authGithubService.login(authResponse);

        verify(oauthSignupService).createGithubPendingSignup(any(UserGithubDTO.class));
        verify(authGithubMapper, never()).saveUserGithubAndUser(any(), any());
        verify(jwtTokenService, never()).generateToken(any());
        verify(sessionAuthService, never()).whitelistToken(any());
        assertEquals(OAuthCallbackWithTokenDTO.SIGNUP_REQUIRED, result.getStatus());
        assertEquals("pending-token", result.getPendingSignupToken());
        assertEquals("github", result.getProvider());
        assertEquals("github-user", result.getSuggestedNickname());
    }

    @Test
    // 已存在用户再次登录时应更新第三方资料并刷新登录态。
    void loginShouldUpdateExistingUserWhenGithubAccountExists() {
        AuthResponse<AuthUser> authResponse = successGithubResponse();
        UserEntity existingUser = existingUser();
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(1);
        when(userService.getUserByGithubId(1001L)).thenReturn(existingUser);
        when(jwtTokenService.generateToken("user-1")).thenReturn("jwt-token");

        OAuthCallbackWithTokenDTO result = authGithubService.login(authResponse);

        verify(authGithubMapper).updateUserGithubByGithubId(any(UserGithubEntity.class));
        verify(userService).updateUserLastLoginByUuid(eq("user-1"), any(), eq("GITHUB_OAUTH"));
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord("user-1", "GITHUB_OAUTH");
        assertEquals(OAuthCallbackWithTokenDTO.LOGIN_SUCCESS, result.getStatus());
        assertEquals("jwt-token", result.getToken());
        assertEquals("GITHUB_OAUTH", result.getLoginSource());
        assertEquals("existing-user", result.getUserInfo().getNickname());
    }

    @Test
    // 授权成功但缺少 GitHub ID 时也应拒绝后续登录流程。
    void loginShouldThrowWhenGithubIdIsMissing() {
        AuthResponse<AuthUser> authResponse = successGithubResponse();
        authResponse.getData().getRawUserInfo().remove("id");

        BizException exception = assertThrows(BizException.class, () -> authGithubService.login(authResponse));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
        verify(authGithubMapper, never()).saveUserGithubAndUser(any(), any());
        verify(oauthSignupService, never()).createGithubPendingSignup(any());
    }

    @Test
    // exists 对空入参应直接返回 false。
    void existsShouldReturnFalseWhenInputIsNull() {
        assertEquals(false, authGithubService.exists(null));
    }

    @Test
    // exists 应根据 mapper 查询结果判断第三方账号是否存在。
    void existsShouldReturnMapperResultWhenGithubIdIsPresent() {
        UserGithubDTO dto = new UserGithubDTO();
        dto.setGithubId(1001L);
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(1);

        boolean exists = authGithubService.exists(dto);

        assertEquals(true, exists);
    }

    private AuthResponse<AuthUser> successGithubResponse() {
        AuthToken token = new AuthToken();
        token.setAccessToken("github-token");

        JSONObject rawUserInfo = new JSONObject();
        rawUserInfo.put("id", 1001L);
        rawUserInfo.put("name", "github-user");
        rawUserInfo.put("avatar_url", "https://avatar/github.png");
        rawUserInfo.put("email", "github@example.com");
        rawUserInfo.put("bio", "github bio");
        rawUserInfo.put("html_url", "https://github.com/demo");

        AuthUser authUser = new AuthUser();
        authUser.setToken(token);
        authUser.setRawUserInfo(rawUserInfo);

        return new AuthResponse<>(2000, "ok", authUser);
    }

    private UserEntity existingUser() {
        UserEntity user = new UserEntity();
        user.setUuid("user-1");
        user.setNickName("existing-user");
        user.setEmail("existing@example.com");
        user.setAvatarUrl("https://avatar/existing.png");
        user.setLastLoginSource("LEGACY");
        return user;
    }

    private OAuthPendingSignupResultDTO pendingSignup() {
        OAuthPendingSignupResultDTO pendingSignup = new OAuthPendingSignupResultDTO();
        pendingSignup.setPendingSignupToken("pending-token");
        pendingSignup.setProvider("github");
        pendingSignup.setAvatarUrl("https://avatar/github.png");
        pendingSignup.setSuggestedNickname("github-user");
        pendingSignup.setEmail("github@example.com");
        return pendingSignup;
    }
}
