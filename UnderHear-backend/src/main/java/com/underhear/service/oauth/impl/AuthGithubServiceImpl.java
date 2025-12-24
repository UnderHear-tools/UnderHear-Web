package com.underhear.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.service.oauth.AuthGithubService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@Service
public class AuthGithubServiceImpl implements AuthGithubService {

    @Override
    public String login(AuthResponse<AuthUser> authResponse) {
        if (authResponse == null) {
            throw new IllegalArgumentException("authResponse is null");
        }
        if (!authResponse.ok()) {
            String message = authResponse.getMsg() == null ? "unknown error" : authResponse.getMsg();
            throw new IllegalStateException("github auth failed: " + message);
        }
        AuthUser authUser = authResponse.getData();
        if (authUser == null) {
            throw new IllegalStateException("github user info is empty");
        }
        AuthToken token = authUser.getToken();
        if (token == null || token.getAccessToken() == null || token.getAccessToken().isBlank()) {
            throw new IllegalStateException("github access token is empty");
        }

        UserGithub userGithub = toUserGithub(authUser, token);
        // TODO: persist userGithub if needed.

        return token.getAccessToken();
    }

    private UserGithub toUserGithub(AuthUser authUser, AuthToken token) {
        UserGithub user = new UserGithub();
        user.setGithubId(parseGithubId(authUser.getUuid()));
        user.setLogin(authUser.getUsername());
        user.setName(authUser.getNickname());
        user.setAvatarUrl(authUser.getAvatar());
        user.setEmail(authUser.getEmail());
        user.setBio(authUser.getRemark());
        user.setHtmlUrl(resolveHtmlUrl(authUser));
        user.setGithubToken(token.getAccessToken());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private Long parseGithubId(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(uuid);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String resolveHtmlUrl(AuthUser authUser) {
        JSONObject rawUserInfo = authUser.getRawUserInfo();
        if (rawUserInfo == null) {
            return null;
        }
        return rawUserInfo.getString("html_url");
    }
}
