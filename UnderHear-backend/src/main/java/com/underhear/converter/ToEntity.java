package com.underhear.converter;

import java.time.LocalDateTime;

import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.request.UserGiteeDort;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGitee;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.util.ApplicationUuidGenerator;
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
        userGithub.setGithubId(userGithubDort.getGithubId());
        userGithub.setName(userGithubDort.getName());
        userGithub.setAvatarUrl(userGithubDort.getAvatarUrl());
        userGithub.setEmail(userGithubDort.getEmail());
        userGithub.setBio(userGithubDort.getBio());
        userGithub.setHtmlUrl(userGithubDort.getHtmlUrl());
        userGithub.setGithubToken(userGithubDort.getGithubToken());
        return userGithub;
    }

    //github第一次登录时用到
    public static User toUser(UserGithub userGithub) {
        User user = new User();
        user.setUuid(userGithub.getUuid());
        user.setNickName(userGithub.getName());
        user.setAvatarUrl(userGithub.getAvatarUrl());
        user.setLastLoginSource("GITHUB_OAUTH");
        return user;
    }

    //gitee第一次登录时用到
    public static UserGitee toUserGitee(UserGiteeDort userGiteeDort) {
        UserGitee userGitee = new UserGitee();
        userGitee.setUuid(ShortUuidGenerator.next());
        userGitee.setGiteeId(userGiteeDort.getGiteeId());
        userGitee.setName(userGiteeDort.getName());
        userGitee.setAvatarUrl(userGiteeDort.getAvatarUrl());
        userGitee.setEmail(userGiteeDort.getEmail());
        userGitee.setBio(userGiteeDort.getBio());
        userGitee.setHtmlUrl(userGiteeDort.getHtmlUrl());
        userGitee.setGiteeToken(userGiteeDort.getGiteeToken());
        return userGitee;
    }

    //gitee非第一次登录时用到
    public static UserGitee toUpdateUserGitee(UserGiteeDort userGiteeDort) {
        UserGitee userGitee = new UserGitee();
        userGitee.setGiteeId(userGiteeDort.getGiteeId());
        userGitee.setName(userGiteeDort.getName());
        userGitee.setAvatarUrl(userGiteeDort.getAvatarUrl());
        userGitee.setEmail(userGiteeDort.getEmail());
        userGitee.setBio(userGiteeDort.getBio());
        userGitee.setHtmlUrl(userGiteeDort.getHtmlUrl());
        userGitee.setGiteeToken(userGiteeDort.getGiteeToken());
        return userGitee;
    }

    //gitee第一次登录时用到
    public static User toUser(UserGitee userGitee) {
        User user = new User();
        user.setUuid(userGitee.getUuid());
        user.setNickName(userGitee.getName());
        user.setAvatarUrl(userGitee.getAvatarUrl());
        user.setLastLoginSource("GITEE_OAUTH");
        return user;
    }
    

    public static User toUpdateUser(User user, LocalDateTime lastLoginAt, String lastLoginSource) {
        user.setLastLoginAt(lastLoginAt);
        user.setLastLoginSource(lastLoginSource);
        return user;
    }

    public static Application toApplication(User user, ApplicationCreateNewDort request) {
        Application application = new Application();
        String appid = ApplicationUuidGenerator.next();
        String ownerUuid = user.getUuid();
        String originalFilename = request.getAppFile().getOriginalFilename();

        application.setAppid(appid);
        application.setOwnerUuid(ownerUuid);
        application.setCreationMethod("NEW");
        application.setFramework(request.getFramework());
        application.setAppName(request.getAppName());
        application.setAppEnglishName(request.getAppEnglishName());
        application.setAppUrl("https://" + request.getAppEnglishName() + ".underhear.cn/");
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        application.setStoragePath(ownerUuid + "/" + appid + "/" + originalFilename);
        application.setOriginalFilename(originalFilename);
        application.setFileType(request.getAppFile().getContentType());
        application.setFileSize(Double.valueOf(request.getAppFile().getSize()));
        return application;
    }
}
