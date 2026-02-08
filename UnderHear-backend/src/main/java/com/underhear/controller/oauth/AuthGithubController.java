package com.underhear.controller.oauth;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.security.AuthCookieService;
import com.underhear.security.SessionAuthService;
import com.underhear.service.oauth.AuthGithubService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/oauth/github")
public class AuthGithubController {

    @Autowired
    private AuthGithubService authGithubService;

    @Autowired
    private AuthCookieService authCookieService;
    @Autowired
    private SessionAuthService sessionAuthService;

    @Value("${github.oauth.client-id}")
    private String clientId;

    @Value("${github.oauth.client-secret}")
    private String clientSecret;

    @Value("${github.oauth.redirect-uri}")
    private String redirectUri;

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public ApiResponse<UserLoginDore> login(
            AuthCallback callback,
            @CookieValue(value = "auth_token", required = false) String oldToken,
            HttpServletResponse response) {
        // 如果已经有 token 还请求登录接口 就先把之前的 token 失效掉 避免同一用户多个 token 共存
        sessionAuthService.logoutIfPresent(oldToken);
        
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        UserLoginDore userLoginDore = authGithubService.login(authResponse);
        authCookieService.writeToken(response, userLoginDore.getToken());
        return ApiResponse.success(userLoginDore);
    }

    private AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }
}
