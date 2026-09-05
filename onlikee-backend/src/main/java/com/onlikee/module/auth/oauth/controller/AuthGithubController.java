package com.onlikee.module.auth.oauth.controller;

import com.onlikee.module.auth.oauth.converter.ToVO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.module.auth.oauth.model.vo.OAuthCallbackVO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.module.auth.service.AuthCookieService;
import com.onlikee.module.auth.service.SessionAuthService;
import com.onlikee.module.auth.oauth.service.AuthGithubService;

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

    @GetMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @GetMapping("/callback")
    public ApiResponse<OAuthCallbackVO> login(
            AuthCallback callback,
            @CookieValue(value = "auth_token", required = false) String oldToken,
            HttpServletResponse response) {
        // 如果已经有 token 还请求登录接口 就先把之前的 token 失效掉 避免同一用户多个 token 共存
        sessionAuthService.logoutIfPresent(oldToken);
        
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        OAuthCallbackWithTokenDTO callbackWithToken = authGithubService.login(authResponse);
        if (OAuthCallbackWithTokenDTO.LOGIN_SUCCESS.equals(callbackWithToken.getStatus())) {
            authCookieService.writeToken(response, callbackWithToken.getToken());
        }
        return ApiResponse.success(ToVO.toOAuthCallbackVO(callbackWithToken));
    }

    private AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }
}
