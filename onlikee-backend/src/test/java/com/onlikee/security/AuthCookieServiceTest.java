package com.onlikee.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

class AuthCookieServiceTest {

    @Test
    // 写入登录态时应输出项目约定的认证 Cookie 属性。
    void writeTokenShouldAddExpectedCookieHeader() {
        AuthCookieService authCookieService = new AuthCookieService();
        MockHttpServletResponse response = new MockHttpServletResponse();

        authCookieService.writeToken(response, "jwt-token");

        String setCookie = response.getHeader(HttpHeaders.SET_COOKIE);
        assertNotNull(setCookie);
        assertTrue(setCookie.contains("auth_token=jwt-token"));
        assertTrue(setCookie.contains("HttpOnly"));
        assertTrue(setCookie.contains("SameSite=Lax"));
        assertTrue(setCookie.contains("Path=/"));
        assertTrue(setCookie.contains("Max-Age=604800"));
        assertFalse(setCookie.contains("Secure"));
    }

    @Test
    // 清理登录态时应把 Cookie 过期时间设为 0。
    void clearTokenShouldAddExpiredCookieHeader() {
        AuthCookieService authCookieService = new AuthCookieService();
        MockHttpServletResponse response = new MockHttpServletResponse();

        authCookieService.clearToken(response);

        String setCookie = response.getHeader(HttpHeaders.SET_COOKIE);
        assertNotNull(setCookie);
        assertTrue(setCookie.contains("auth_token="));
        assertTrue(setCookie.contains("HttpOnly"));
        assertTrue(setCookie.contains("SameSite=Lax"));
        assertTrue(setCookie.contains("Path=/"));
        assertTrue(setCookie.contains("Max-Age=0"));
    }
}
