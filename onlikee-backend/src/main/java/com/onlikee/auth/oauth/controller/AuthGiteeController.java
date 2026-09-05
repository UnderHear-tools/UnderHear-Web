package com.onlikee.auth.oauth.controller;

import com.onlikee.auth.oauth.converter.ToDore;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackDore;
import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackWithTokenDore;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.auth.service.AuthCookieService;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.auth.oauth.service.AuthGiteeService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/oauth/gitee")
public class AuthGiteeController {

    @Autowired
    private AuthGiteeService authGiteeService;

    @Autowired
    private AuthCookieService authCookieService;
    @Autowired
    private SessionAuthService sessionAuthService;

    @Value("${gitee.oauth.client-id}")
    private String clientId;

    @Value("${gitee.oauth.client-secret}")
    private String clientSecret;

    @Value("${gitee.oauth.redirect-uri}")
    private String redirectUri;

    @GetMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @GetMapping("/callback")
    public ApiResponse<OAuthCallbackDore> login(
            AuthCallback callback,
            @CookieValue(value = "auth_token", required = false) String oldToken,
            HttpServletResponse response) {
        // 如果已经有 token 还请求登录接口 就先把之前的 token 失效掉 避免同一用户多个 token 共存
        sessionAuthService.logoutIfPresent(oldToken);
        
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        OAuthCallbackWithTokenDore callbackWithToken = authGiteeService.login(authResponse);
        if (OAuthCallbackWithTokenDore.LOGIN_SUCCESS.equals(callbackWithToken.getStatus())) {
            authCookieService.writeToken(response, callbackWithToken.getToken());
        }
        return ApiResponse.success(ToDore.toOAuthCallbackDore(callbackWithToken));
    }

    private AuthRequest getAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }
}
