package com.underhear.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthCookieService {

    private static final boolean HTTP_ONLY = true;
    private static final String COOKIE_NAME = "auth_token";
    private static final String SAME_SITE = "Lax";
    private static final boolean SECURE = false;
    private static final String PATH = "/";
    private static final long MAX_AGE_SECONDS = 60L * 60 * 24 * 7;

    public void writeToken(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(HTTP_ONLY)
                .secure(SECURE)
                .path(PATH)
                .sameSite(SAME_SITE)
                .maxAge(MAX_AGE_SECONDS)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearToken(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(HTTP_ONLY)
                .secure(SECURE)
                .path(PATH)
                .sameSite(SAME_SITE)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
