package com.onlikee.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.user.model.entity.User;
import com.onlikee.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class SessionAuthServiceTest {

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private UserService userService;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private SetOperations<String, String> setOperations;

    private SessionAuthService sessionAuthService;

    @BeforeEach
    // Redis 读写入口统一在这里打桩，避免每个测试重复配置。
    void setUp() {
        sessionAuthService = new SessionAuthService(jwtTokenService, stringRedisTemplate, userService, 60L);
        lenient().when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        lenient().when(stringRedisTemplate.opsForSet()).thenReturn(setOperations);
    }

    @Test
    // 白名单写入时应同时维护 token->uuid 和 user->tokens 两份索引。
    void whitelistTokenShouldWriteRedisWhitelistEntries() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);

        sessionAuthService.whitelistToken("token");

        verify(valueOperations).set("auth:token:token-id", "user-1", 60L, TimeUnit.SECONDS);
        verify(setOperations).add("auth:user:tokens:user-1", "token-id");
        verify(stringRedisTemplate).expire("auth:user:tokens:user-1", 60L, TimeUnit.SECONDS);
    }

    @Test
    // 白名单命中时应返回当前登录用户。
    void getCurrentUserShouldReturnUserWhenTokenIsWhitelisted() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        User user = user();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);
        when(valueOperations.get("auth:token:token-id")).thenReturn("user-1");
        when(userService.getUserByUuid("user-1")).thenReturn(user);

        User currentUser = sessionAuthService.getCurrentUser("token");

        assertSame(user, currentUser);
    }

    @Test
    // 白名单缺失时应按未登录处理。
    void getCurrentUserShouldThrowWhenWhitelistEntryIsMissing() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);
        when(valueOperations.get("auth:token:token-id")).thenReturn(null);

        BizException exception = assertThrows(BizException.class, () -> sessionAuthService.getCurrentUser("token"));

        assertEquals(ErrorCode.NOT_LOGIN.getCode(), exception.getCode());
        verify(userService, never()).getUserByUuid("user-1");
    }

    @Test
    // JWT 解析失败时也应统一映射成未登录。
    void getCurrentUserShouldTranslateParseFailureToNotLogin() {
        when(jwtTokenService.parseToken("bad-token")).thenThrow(new IllegalArgumentException("bad token"));

        BizException exception = assertThrows(BizException.class, () -> sessionAuthService.getCurrentUser("bad-token"));

        assertEquals(ErrorCode.NOT_LOGIN.getCode(), exception.getCode());
    }

    @Test
    // 单点退出时应删除当前 token 的白名单记录。
    void logoutShouldDeleteCurrentTokenEntries() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);

        sessionAuthService.logout("token");

        verify(stringRedisTemplate).delete("auth:token:token-id");
        verify(setOperations).remove("auth:user:tokens:user-1", "token-id");
    }

    @Test
    // 全部退出时应删除该用户的所有 token 白名单记录。
    void logoutAllShouldDeleteAllTokenEntriesForCurrentUser() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);
        when(setOperations.members("auth:user:tokens:user-1")).thenReturn(Set.of("token-a", "token-b"));

        sessionAuthService.logoutAll("token");

        verify(stringRedisTemplate).delete(Set.of("auth:token:token-a", "auth:token:token-b"));
        verify(stringRedisTemplate).delete("auth:user:tokens:user-1");
    }

    @Test
    // 已有 token 时应复用 logout 流程做失效处理。
    void logoutIfPresentShouldDelegateToLogoutWhenTokenExists() {
        JwtTokenService.JwtTokenPayload payload = tokenPayload();
        when(jwtTokenService.parseToken("token")).thenReturn(payload);

        sessionAuthService.logoutIfPresent("token");

        verify(stringRedisTemplate).delete("auth:token:token-id");
        verify(setOperations).remove("auth:user:tokens:user-1", "token-id");
    }

    @Test
    // token 缺失时不应对 Redis 发起任何写操作。
    void logoutIfPresentShouldDoNothingWhenTokenIsNull() {
        sessionAuthService.logoutIfPresent(null);

        verifyNoInteractions(jwtTokenService);
        verify(stringRedisTemplate, never()).delete(anyCollection());
        verify(stringRedisTemplate, never()).delete(eq("auth:token:token-id"));
        verify(stringRedisTemplate, never()).expire(eq("auth:user:tokens:user-1"), anyLong(), eq(TimeUnit.SECONDS));
    }

    private JwtTokenService.JwtTokenPayload tokenPayload() {
        return new JwtTokenService.JwtTokenPayload(
                "token-id",
                "user-1",
                Instant.parse("2026-04-13T00:00:00Z"),
                Instant.parse("2026-04-13T00:01:00Z"));
    }

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        return user;
    }
}
