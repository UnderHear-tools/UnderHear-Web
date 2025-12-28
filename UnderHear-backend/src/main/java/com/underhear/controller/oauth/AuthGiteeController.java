package com.underhear.controller.oauth;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.service.oauth.AuthGiteeService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/oauth/gitee")
public class AuthGiteeController {

    @Autowired
    private AuthGiteeService authGiteeService;
    
    @Value("${gitee.oauth.client-id}")
    private String clientId;

    @Value("${gitee.oauth.client-secret}")
    private String clientSecret;

    @Value("${gitee.oauth.redirect-uri}")
    private String redirectUri;

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public ApiResponse<UserLoginDore> login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        UserLoginDore userLoginDore = authGiteeService.login(authResponse);
        return ApiResponse.success(userLoginDore);
    }

    private AuthRequest getAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }
}
