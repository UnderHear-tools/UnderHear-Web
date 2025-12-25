package com.underhear.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.underhear.pojo.dto.request.UserGithubDORT;
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
        UserGithubDORT userGithubDORT = toUserGithubDORT(authUser.getRawUserInfo(), token);
        UserGithub userGithub = toUserGithub(userGithubDORT);
        // TODO: persist userGithub if needed.

        return token.getAccessToken();
    }

    private UserGithubDORT toUserGithubDORT(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGithubDORT userGithubDORT = new UserGithubDORT();
        userGithubDORT.setGithubId(rawUserInfoJSON.getLong("id"));
        userGithubDORT.setLogin(rawUserInfoJSON.getString("login"));
        userGithubDORT.setName(rawUserInfoJSON.getString("name"));
        userGithubDORT.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGithubDORT.setEmail(rawUserInfoJSON.getString("email"));
        userGithubDORT.setBio(rawUserInfoJSON.getString("bio"));
        userGithubDORT.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGithubDORT.setGithubToken(token.getAccessToken());
        return userGithubDORT;
    }

    private UserGithub toUserGithub(UserGithubDORT userGithubDORT) {
        UserGithub userGithub = new UserGithub();
        userGithub.setGithubId(userGithubDORT.getGithubId());
        userGithub.setLogin(userGithubDORT.getLogin());
        userGithub.setName(userGithubDORT.getName());
        userGithub.setAvatarUrl(userGithubDORT.getAvatarUrl());
        userGithub.setEmail(userGithubDORT.getEmail());
        userGithub.setBio(userGithubDORT.getBio());
        userGithub.setHtmlUrl(userGithubDORT.getHtmlUrl());
        userGithub.setGithubToken(userGithubDORT.getGithubToken());
        userGithub.setCreatedAt(LocalDateTime.now());
        userGithub.setUpdatedAt(LocalDateTime.now());
        return userGithub;
    }

}
