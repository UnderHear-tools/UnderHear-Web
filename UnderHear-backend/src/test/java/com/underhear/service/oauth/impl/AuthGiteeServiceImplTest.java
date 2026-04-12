package com.underhear.service.oauth.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.underhear.mapper.oauth.AuthGiteeMapper;
import com.underhear.pojo.dto.request.UserGiteeDort;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGitee;
import com.underhear.security.JwtTokenService;
import com.underhear.security.SessionAuthService;
import com.underhear.service.user.UserService;

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

    @InjectMocks
    private AuthGiteeServiceImpl authGiteeService;

    @Test
    // 授权结果无效时应直接按授权失败处理。
    void loginShouldThrowWhenAuthorizationFails() {
        BizException exception = assertThrows(BizException.class, () -> authGiteeService.login(null));

        assertEquals(ErrorCode.BAD_AUTHORIZED.getCode(), exception.getCode());
    }

    @Test
    // 第一次登录时应写入第三方映射、用户记录、token 白名单和登录日志。
    void loginShouldRegisterUserWhenGiteeAccountDoesNotExist() {
        AuthResponse<AuthUser> authResponse = successGiteeResponse();
        when(authGiteeMapper.countByGiteeId(2002L)).thenReturn(0);
        when(jwtTokenService.generateToken(any())).thenReturn("jwt-token");

        UserLoginWithTokenDore result = authGiteeService.login(authResponse);

        ArgumentCaptor<UserGitee> userGiteeCaptor = ArgumentCaptor.forClass(UserGitee.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(authGiteeMapper).saveUserGiteeAndUser(userGiteeCaptor.capture(), userCaptor.capture());
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord(userCaptor.getValue().getUuid(), "GITEE_OAUTH");

        UserGitee savedGiteeUser = userGiteeCaptor.getValue();
        User savedUser = userCaptor.getValue();
        assertEquals(savedGiteeUser.getUuid(), savedUser.getUuid());
        assertEquals("gitee-user", savedGiteeUser.getName());
        assertEquals("gitee-token", savedGiteeUser.getGiteeToken());
        assertEquals("gitee-user", savedUser.getNickName());
        assertEquals("GITEE_OAUTH", result.getLoginSource());
        assertEquals("jwt-token", result.getToken());
        assertEquals("gitee-user", result.getUserInfo().getNickname());
    }

    @Test
    // 已存在用户再次登录时应更新第三方资料并刷新登录态。
    void loginShouldUpdateExistingUserWhenGiteeAccountExists() {
        AuthResponse<AuthUser> authResponse = successGiteeResponse();
        User existingUser = existingUser();
        when(authGiteeMapper.countByGiteeId(2002L)).thenReturn(1);
        when(userService.getUserByGiteeId(2002L)).thenReturn(existingUser);
        when(jwtTokenService.generateToken("user-1")).thenReturn("jwt-token");

        UserLoginWithTokenDore result = authGiteeService.login(authResponse);

        verify(authGiteeMapper).updateUserGiteeByGiteeId(any(UserGitee.class));
        verify(userService).updateUserLastLoginByUuid(eq("user-1"), any(), eq("GITEE_OAUTH"));
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord("user-1", "GITEE_OAUTH");
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
}
