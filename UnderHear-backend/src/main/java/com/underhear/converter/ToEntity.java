package com.underhear.converter;

import java.time.LocalDateTime;

import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.util.ShortUuidGenerator;

public final class ToEntity {

    private ToEntity() {
    }

    //github第一次登录时用到
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

    //github非第一次登录时用到
    public static UserGithub toUpdateUserGithub(UserGithubDort userGithubDort) {
        UserGithub userGithub = new UserGithub();
        userGithub.setName(userGithubDort.getName());
        userGithub.setAvatarUrl(userGithubDort.getAvatarUrl());
        userGithub.setEmail(userGithubDort.getEmail());
        userGithub.setBio(userGithubDort.getBio());
        userGithub.setHtmlUrl(userGithubDort.getHtmlUrl());
        userGithub.setGithubToken(userGithubDort.getGithubToken());
        return userGithub;
    }

    //github第一次登录时会用
    public static User toUser(UserGithub userGithub) {
        User user = new User();
        user.setUuid(userGithub.getUuid());
        user.setNickName(userGithub.getName());
        user.setAvatarUrl(userGithub.getAvatarUrl());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginSource("GITHUB_OAUTH");
        return user;
    }
    

    public static User toUpdateUser(User user, LocalDateTime lastLoginAt, String lastLoginSource) {
        user.setLastLoginAt(lastLoginAt);
        user.setLastLoginSource(lastLoginSource);
        return user;
    }
}
