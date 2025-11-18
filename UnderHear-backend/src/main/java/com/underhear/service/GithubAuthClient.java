package com.underhear.service;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.underhear.config.GithubOAuthProperties;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

@Component
public class GithubAuthClient {

    private final GithubOAuthProperties properties;

    public GithubAuthClient(GithubOAuthProperties properties) {
        this.properties = properties;
    }

    public String buildAuthorizeUrl(String state) {
        validateConfig();
        String resolvedState = StringUtils.hasText(state) ? state : AuthStateUtils.createState();
        return createAuthRequest().authorize(resolvedState);
    }

    @SuppressWarnings("unchecked")
    public AuthResponse<AuthUser> login(String code, String state) {
        validateConfig();
        AuthRequest authRequest = createAuthRequest();
        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        return authRequest.login(callback);
    }

    private AuthRequest createAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .redirectUri(properties.getRedirectUri())
                .build());
    }

    private void validateConfig() {
        Assert.hasText(properties.getClientId(), "GitHub clientId must be configured");
        Assert.hasText(properties.getClientSecret(), "GitHub clientSecret must be configured");
        Assert.hasText(properties.getRedirectUri(), "GitHub redirectUri must be configured");
    }
}

