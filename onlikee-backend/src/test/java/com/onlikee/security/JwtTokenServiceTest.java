package com.onlikee.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.JwtException;

class JwtTokenServiceTest {

    private static final String SECRET = "12345678901234567890123456789012";

    @Test
    // secret 为空时应在构造阶段直接失败。
    void constructorShouldRejectBlankSecret() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new JwtTokenService(" ", 60));

        assertEquals("security.jwt.secret is required", exception.getMessage());
    }

    @Test
    // secret 长度不足 32 时无法满足当前 HMAC 配置要求。
    void constructorShouldRejectShortSecret() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new JwtTokenService("short-secret", 60));

        assertEquals("security.jwt.secret must be at least 32 characters", exception.getMessage());
    }

    @Test
    // 生成并解析 token 时应保留当前用户标识和时间信息。
    void generateAndParseTokenShouldRoundTripPayload() {
        JwtTokenService jwtTokenService = new JwtTokenService(SECRET, 120);

        String token = jwtTokenService.generateToken("user-1");
        JwtTokenService.JwtTokenPayload payload = jwtTokenService.parseToken(token);

        assertNotNull(payload.getTokenId());
        assertEquals("user-1", payload.getUuid());
        assertNotNull(payload.getIssuedAt());
        assertNotNull(payload.getExpiresAt());
        assertTrue(!payload.getExpiresAt().isBefore(payload.getIssuedAt()));
    }

    @Test
    // uuid 为空时不允许生成 token。
    void generateTokenShouldRejectBlankUuid() {
        JwtTokenService jwtTokenService = new JwtTokenService(SECRET, 60);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jwtTokenService.generateToken(" "));

        assertEquals("uuid is required", exception.getMessage());
    }

    @Test
    // token 为空时解析入口应直接拒绝。
    void parseTokenShouldRejectBlankToken() {
        JwtTokenService jwtTokenService = new JwtTokenService(SECRET, 60);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jwtTokenService.parseToken(" "));

        assertEquals("token is required", exception.getMessage());
    }

    @Test
    // 非法 token 字符串应由 JWT 解析器抛出异常。
    void parseTokenShouldRejectMalformedToken() {
        JwtTokenService jwtTokenService = new JwtTokenService(SECRET, 60);

        assertThrows(JwtException.class, () -> jwtTokenService.parseToken("bad-token"));
    }
}
