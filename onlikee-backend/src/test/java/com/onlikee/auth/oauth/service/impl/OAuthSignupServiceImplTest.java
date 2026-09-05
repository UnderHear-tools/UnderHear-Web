package com.onlikee.auth.oauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.auth.oauth.mapper.AuthGiteeMapper;
import com.onlikee.auth.oauth.mapper.AuthGithubMapper;
import com.onlikee.user.mapper.UserMapper;
import com.onlikee.auth.oauth.model.dto.OAuthPendingSignupDTO;
import com.onlikee.auth.oauth.model.dto.OAuthSignupCompleteDTO;
import com.onlikee.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.auth.model.dto.UserLoginWithTokenDTO;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.auth.oauth.model.entity.UserGithubEntity;
import com.onlikee.auth.service.JwtTokenService;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class OAuthSignupServiceImplTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthGithubMapper authGithubMapper;

    @Mock
    private AuthGiteeMapper authGiteeMapper;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private SessionAuthService sessionAuthService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OAuthSignupServiceImpl oauthSignupService;

    @BeforeEach
    void setUp() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    // 首次 OAuth 确认后应只保存短期注册会话，响应中不暴露第三方 token。
    void createGithubPendingSignupShouldStorePendingContext() {
        UserGithubDTO githubDTO = githubDTO();

        OAuthPendingSignupResultDTO result = oauthSignupService.createGithubPendingSignup(githubDTO);

        verify(valueOperations).set(
                argThat(key -> key.startsWith("oauth:pending_signup:")),
                argThat(value -> value.contains("\"provider\":\"github\"")
                        && value.contains("\"providerUserId\":1001")
                        && value.contains("\"providerToken\":\"github-token\"")),
                eq(1800L),
                eq(TimeUnit.SECONDS));
        assertEquals("github", result.getProvider());
        assertEquals("github-user", result.getSuggestedNickname());
        assertEquals("github@example.com", result.getEmail());
    }

    @Test
    // 完善资料成功时应创建 user、OAuth 绑定、登录记录和正式登录 token。
    void completeShouldCreateUserAndGithubBinding() {
        OAuthSignupCompleteDTO request = completeRequest(" pending-token ", " tester ", " tester@example.com ");
        when(valueOperations.setIfAbsent("oauth:pending_signup:lock:pending-token", "1", 30L, TimeUnit.SECONDS))
                .thenReturn(true);
        when(valueOperations.get("oauth:pending_signup:pending-token"))
                .thenReturn(JSON.toJSONString(pendingSignup("github")));
        when(userMapper.countByNickname("tester")).thenReturn(0);
        when(userMapper.countByEmail("tester@example.com")).thenReturn(0);
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(0);
        when(userMapper.insertUser(any(UserEntity.class))).thenReturn(1);
        when(authGithubMapper.insertUserGithub(any(UserGithubEntity.class))).thenReturn(1);
        when(jwtTokenService.generateToken(any())).thenReturn("jwt-token");

        UserLoginWithTokenDTO result = oauthSignupService.complete(request);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<UserGithubEntity> githubCaptor = ArgumentCaptor.forClass(UserGithubEntity.class);
        verify(userMapper).insertUser(userCaptor.capture());
        verify(authGithubMapper).insertUserGithub(githubCaptor.capture());
        verify(sessionAuthService).whitelistToken("jwt-token");
        verify(userService).insertUserLoginRecord(userCaptor.getValue().getUuid(), "GITHUB_OAUTH");
        verify(stringRedisTemplate).delete("oauth:pending_signup:pending-token");
        verify(stringRedisTemplate, never()).delete("oauth:pending_signup:lock:pending-token");

        assertEquals("tester", userCaptor.getValue().getNickName());
        assertEquals("tester@example.com", userCaptor.getValue().getEmail());
        assertEquals(userCaptor.getValue().getUuid(), githubCaptor.getValue().getUuid());
        assertEquals("jwt-token", result.getToken());
        assertEquals("tester", result.getUserInfo().getNickname());
    }

    @Test
    // pending token 不存在时应拒绝完成注册并释放提交锁。
    void completeShouldRejectInvalidPendingToken() {
        OAuthSignupCompleteDTO request = completeRequest("pending-token", "tester", "tester@example.com");
        when(valueOperations.setIfAbsent("oauth:pending_signup:lock:pending-token", "1", 30L, TimeUnit.SECONDS))
                .thenReturn(true);
        when(valueOperations.get("oauth:pending_signup:pending-token")).thenReturn(null);

        BizException exception = assertThrows(BizException.class, () -> oauthSignupService.complete(request));

        assertEquals(ErrorCode.PENDING_SIGNUP_INVALID.getCode(), exception.getCode());
        verify(stringRedisTemplate).delete("oauth:pending_signup:lock:pending-token");
        verify(userMapper, never()).insertUser(any());
    }

    @Test
    // 同一个 pending token 正在提交时应拒绝重复提交。
    void completeShouldRejectWhenPendingTokenIsLocked() {
        OAuthSignupCompleteDTO request = completeRequest("pending-token", "tester", "tester@example.com");
        when(valueOperations.setIfAbsent("oauth:pending_signup:lock:pending-token", "1", 30L, TimeUnit.SECONDS))
                .thenReturn(false);

        BizException exception = assertThrows(BizException.class, () -> oauthSignupService.complete(request));

        assertEquals(ErrorCode.PENDING_SIGNUP_IN_PROGRESS.getCode(), exception.getCode());
        verify(valueOperations, never()).get(any());
    }

    @Test
    // nickname 已存在时应返回明确业务错误。
    void completeShouldRejectDuplicateNickname() {
        OAuthSignupCompleteDTO request = completeRequest("pending-token", "tester", "tester@example.com");
        when(valueOperations.setIfAbsent("oauth:pending_signup:lock:pending-token", "1", 30L, TimeUnit.SECONDS))
                .thenReturn(true);
        when(valueOperations.get("oauth:pending_signup:pending-token"))
                .thenReturn(JSON.toJSONString(pendingSignup("github")));
        when(userMapper.countByNickname("tester")).thenReturn(1);

        BizException exception = assertThrows(BizException.class, () -> oauthSignupService.complete(request));

        assertEquals(ErrorCode.NICKNAME_ALREADY_EXISTS.getCode(), exception.getCode());
        verify(stringRedisTemplate).delete("oauth:pending_signup:lock:pending-token");
        verify(userMapper, never()).insertUser(any());
    }

    @Test
    // 第三方账号已被绑定时应阻止旧 pending token 继续创建账号。
    void completeShouldRejectAlreadyBoundOAuthAccount() {
        OAuthSignupCompleteDTO request = completeRequest("pending-token", "tester", "tester@example.com");
        when(valueOperations.setIfAbsent("oauth:pending_signup:lock:pending-token", "1", 30L, TimeUnit.SECONDS))
                .thenReturn(true);
        when(valueOperations.get("oauth:pending_signup:pending-token"))
                .thenReturn(JSON.toJSONString(pendingSignup("github")));
        when(userMapper.countByNickname("tester")).thenReturn(0);
        when(userMapper.countByEmail("tester@example.com")).thenReturn(0);
        when(authGithubMapper.countByGithubId(1001L)).thenReturn(1);

        BizException exception = assertThrows(BizException.class, () -> oauthSignupService.complete(request));

        assertEquals(ErrorCode.OAUTH_ACCOUNT_ALREADY_BOUND.getCode(), exception.getCode());
        verify(userMapper, never()).insertUser(any());
    }

    private UserGithubDTO githubDTO() {
        UserGithubDTO githubDTO = new UserGithubDTO();
        githubDTO.setGithubId(1001L);
        githubDTO.setName("github-user");
        githubDTO.setAvatarUrl("https://avatar/github.png");
        githubDTO.setEmail("github@example.com");
        githubDTO.setBio("github bio");
        githubDTO.setHtmlUrl("https://github.com/demo");
        githubDTO.setGithubToken("github-token");
        return githubDTO;
    }

    private OAuthPendingSignupDTO pendingSignup(String provider) {
        OAuthPendingSignupDTO pendingSignup = new OAuthPendingSignupDTO();
        pendingSignup.setProvider(provider);
        pendingSignup.setProviderUserId(1001L);
        pendingSignup.setName("github-user");
        pendingSignup.setAvatarUrl("https://avatar/github.png");
        pendingSignup.setEmail("github@example.com");
        pendingSignup.setBio("github bio");
        pendingSignup.setHtmlUrl("https://github.com/demo");
        pendingSignup.setProviderToken("github-token");
        return pendingSignup;
    }

    private OAuthSignupCompleteDTO completeRequest(String pendingSignupToken, String nickname, String email) {
        OAuthSignupCompleteDTO request = new OAuthSignupCompleteDTO();
        request.setPendingSignupToken(pendingSignupToken);
        request.setNickname(nickname);
        request.setEmail(email);
        return request;
    }
}
