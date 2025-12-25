package com.underhear.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.underhear.mapper.oauth.AuthGithubMapper;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.pojo.entity.User;
import com.underhear.service.api.UserService;
import com.underhear.service.oauth.AuthGithubService;
import com.underhear.util.ShortUuidGenerator;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@Service
public class AuthGithubServiceImpl implements AuthGithubService {

    @Autowired
    private AuthGithubMapper authGithubMapper;
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    //github oauth登录/注册
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
        
        UserGithubDort userGithubDort = toUserGithubDort(authUser.getRawUserInfo(), token);
        
        if (!exists(userGithubDort)) {
            UserGithub userGithub = toUserGithub(userGithubDort);
            User user = toUser(userGithub);
            authGithubMapper.saveUserGithubAndUser(userGithub, user);
        }

        User user = userService.findUserByGithubId(userGithubDort.getGithubId());

        

        return token.getAccessToken();
    }

    private UserGithubDort toUserGithubDort(Object rawUserInfo, AuthToken token) {
        JSONObject rawUserInfoJSON = (JSONObject) rawUserInfo;
        UserGithubDort userGithubDort = new UserGithubDort();
        userGithubDort.setGithubId(rawUserInfoJSON.getLong("id"));
        userGithubDort.setName(rawUserInfoJSON.getString("name"));
        userGithubDort.setAvatarUrl(rawUserInfoJSON.getString("avatar_url"));
        userGithubDort.setEmail(rawUserInfoJSON.getString("email"));
        userGithubDort.setBio(rawUserInfoJSON.getString("bio"));
        userGithubDort.setHtmlUrl(rawUserInfoJSON.getString("html_url"));
        userGithubDort.setGithubToken(token.getAccessToken());
        return userGithubDort;
    }

    private UserGithub toUserGithub(UserGithubDort userGithubDort) {
        UserGithub userGithub = new UserGithub();
        userGithub.setUuid(ShortUuidGenerator.next());
        userGithub.setGithubId(userGithubDort.getGithubId());
        userGithub.setName(userGithubDort.getName());
        userGithub.setAvatarUrl(userGithubDort.getAvatarUrl());
        userGithub.setEmail(userGithubDort.getEmail());
        userGithub.setBio(userGithubDort.getBio());
        userGithub.setHtmlUrl(userGithubDort.getHtmlUrl());
        userGithub.setGithubToken(userGithubDort.getGithubToken());
        return userGithub;
    }

    private User toUser(UserGithub userGithub) {
        User user = new User();
        user.setUuid(userGithub.getUuid());
        user.setNickName(userGithub.getName());
        user.setAvatarUrl(userGithub.getAvatarUrl());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    //在oauth_github表中检测该用户是否存在
    public boolean exists(UserGithubDort userGithubDort) {
        if (userGithubDort == null) {
            return false;
        }
        if (userGithubDort.getGithubId() != null) {
            return authGithubMapper.countByGithubId(userGithubDort.getGithubId()) > 0;
        }
        return false;
    }

}
