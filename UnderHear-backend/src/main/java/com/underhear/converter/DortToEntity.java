package com.underhear.converter;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSONObject;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.util.ShortUuidGenerator;

import me.zhyd.oauth.model.AuthToken;

public final class DortToEntity {

    private DortToEntity() {
    }

    public static UserGithub toUserGithub(UserGithubDort userGithubDort) {
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

    public static UserGithubDort toUserGithubDort(Object rawUserInfo, AuthToken token) {
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

    public static User toUser(UserGithub userGithub) {
        User user = new User();
        user.setUuid(userGithub.getUuid());
        user.setNickName(userGithub.getName());
        user.setAvatarUrl(userGithub.getAvatarUrl());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
    
}
