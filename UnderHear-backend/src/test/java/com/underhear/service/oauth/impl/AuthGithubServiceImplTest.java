package com.underhear.service.oauth.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.fastjson.JSONObject;
import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.oauth.AuthGithubMapper;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.security.JwtTokenService;
import com.underhear.security.SessionAuthService;
import com.underhear.service.user.UserService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@ExtendWith(MockitoExtension.class)
class AuthGithubServiceImplTest {

    @Mock
    private AuthGithubMapper authGithubMapper;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private SessionAuthService sessionAuthService;

    @InjectMocks
    private AuthGithubServiceImpl authGithubService;

    @Test
    // 授权结果无效时应直接按授权失败处理。
    void loginShouldThrowWhenAuthorizationFails() {
        BizException exception = assertThrows(BizException.class, () -> authGithubService.login(null));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
    }

    @Test
    // 第一次登录时应写入第三方映射、用户记录、token 白名单和登录日志。
    void loginShouldRegisterUserWhenGithubAccountDoesNotExist() {
        AuthResponse<AuthUser> authResponse = successGithubResponse();
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(0);
        when(jwtTokenService.generateToken(any())).thenReturn("jwt-token");

        UserLoginWithTokenDore result = authGithubService.login(authResponse);

        ArgumentCaptor<UserGithub> userGithubCaptor = ArgumentCaptor.forClass(UserGithub.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(authGithubMapper).saveUserGithubAndUser(userGithubCaptor.capture(), userCaptor.capture());
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord(userCaptor.getValue().getUuid(), "GITHUB_OAUTH");

        UserGithub savedGithubUser = userGithubCaptor.getValue();
        User savedUser = userCaptor.getValue();
        assertEquals(savedGithubUser.getUuid(), savedUser.getUuid());
        assertEquals("github-user", savedGithubUser.getName());
        assertEquals("github-token", savedGithubUser.getGithubToken());
        assertEquals("github-user", savedUser.getNickName());
        assertEquals("GITHUB_OAUTH", result.getLoginSource());
        assertEquals("jwt-token", result.getToken());
        assertEquals("github-user", result.getUserInfo().getNickname());
    }

    @Test
    // 已存在用户再次登录时应更新第三方资料并刷新登录态。
    void loginShouldUpdateExistingUserWhenGithubAccountExists() {
        AuthResponse<AuthUser> authResponse = successGithubResponse();
        User existingUser = existingUser();
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(1);
        when(userService.getUserByGithubId(1001L)).thenReturn(existingUser);
        when(jwtTokenService.generateToken("user-1")).thenReturn("jwt-token");

        UserLoginWithTokenDore result = authGithubService.login(authResponse);

        verify(authGithubMapper).updateUserGithubByGithubId(any(UserGithub.class));
        verify(userService).updateUserLastLoginByUuid(eq("user-1"), any(), eq("GITHUB_OAUTH"));
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord("user-1", "GITHUB_OAUTH");
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
    }

    @Test
    // exists 对空入参应直接返回 false。
    void existsShouldReturnFalseWhenInputIsNull() {
        assertEquals(false, authGithubService.exists(null));
    }

    @Test
    // exists 应根据 mapper 查询结果判断第三方账号是否存在。
    void existsShouldReturnMapperResultWhenGithubIdIsPresent() {
        UserGithubDort dort = new UserGithubDort();
        dort.setGithubId(1001L);
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(1);

        boolean exists = authGithubService.exists(dort);

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

    private User existingUser() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("existing-user");
        user.setEmail("existing@example.com");
        user.setAvatarUrl("https://avatar/existing.png");
        user.setLastLoginSource("LEGACY");
        return user;
    }
}
