package com.onlikee.converter;

import java.time.LocalDateTime;

import com.onlikee.pojo.dto.request.ApplicationCreateConnectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.request.OAuthPendingSignupDort;
import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.request.UserGithubDort;
import com.onlikee.pojo.entity.Application;
import com.onlikee.pojo.entity.User;
import com.onlikee.pojo.entity.UserGitee;
import com.onlikee.pojo.entity.UserGithub;
import com.onlikee.util.ApplicationUuidGenerator;
import com.onlikee.util.FileSizeFormatter;
import com.onlikee.util.ShortUuidGenerator;

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
        user.setEmail(userGithub.getEmail());
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
        user.setEmail(userGitee.getEmail());
        user.setAvatarUrl(userGitee.getAvatarUrl());
        user.setLastLoginSource("GITEE_OAUTH");
        return user;
    }
    

    public static User toUpdateUser(User user, LocalDateTime lastLoginAt, String lastLoginSource) {
        user.setLastLoginAt(lastLoginAt);
        user.setLastLoginSource(lastLoginSource);
        return user;
    }

    public static User toOAuthSignupUser(OAuthPendingSignupDort pendingSignup, String uuid, String nickname, String email) {
        User user = new User();
        user.setUuid(uuid);
        user.setNickName(nickname);
        user.setEmail(email);
        user.setAvatarUrl(pendingSignup.getAvatarUrl());
        user.setLastLoginSource(toLoginSource(pendingSignup.getProvider()));
        return user;
    }

    public static UserGithub toUserGithub(OAuthPendingSignupDort pendingSignup, String uuid) {
        UserGithub userGithub = new UserGithub();
        userGithub.setUuid(uuid);
        userGithub.setGithubId(pendingSignup.getProviderUserId());
        userGithub.setName(pendingSignup.getName());
        userGithub.setAvatarUrl(pendingSignup.getAvatarUrl());
        userGithub.setEmail(pendingSignup.getEmail());
        userGithub.setBio(pendingSignup.getBio());
        userGithub.setHtmlUrl(pendingSignup.getHtmlUrl());
        userGithub.setGithubToken(pendingSignup.getProviderToken());
        return userGithub;
    }

    public static UserGitee toUserGitee(OAuthPendingSignupDort pendingSignup, String uuid) {
        UserGitee userGitee = new UserGitee();
        userGitee.setUuid(uuid);
        userGitee.setGiteeId(pendingSignup.getProviderUserId());
        userGitee.setName(pendingSignup.getName());
        userGitee.setAvatarUrl(pendingSignup.getAvatarUrl());
        userGitee.setEmail(pendingSignup.getEmail());
        userGitee.setBio(pendingSignup.getBio());
        userGitee.setHtmlUrl(pendingSignup.getHtmlUrl());
        userGitee.setGiteeToken(pendingSignup.getProviderToken());
        return userGitee;
    }

    public static String toLoginSource(String provider) {
        if ("github".equals(provider)) {
            return "GITHUB_OAUTH";
        }
        if ("gitee".equals(provider)) {
            return "GITEE_OAUTH";
        }
        return "OAUTH";
    }

    public static Application toApplication(User user, ApplicationCreateNewDort request) {
        Application application = new Application();
        String appid = ApplicationUuidGenerator.next();
        String originalFilename = request.getAppFile().getOriginalFilename();

        application.setAppid(appid);
        application.setOwnerUuid(user.getUuid());
        application.setCreationMethod("new");
        application.setFramework(request.getFramework());
        application.setAppName(request.getAppName());
        application.setAppUrl(request.getAppUrl());
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        application.setOriginalFilename(originalFilename);
        application.setOriginalFileType(request.getAppFile().getContentType());
        application.setOriginalFileSize(FileSizeFormatter.format(request.getAppFile().getSize()));
        return application;
    }

    public static Application toApplication(User user, ApplicationCreateConnectDort request, String appUrl) {
        Application application = new Application();
        String appid = ApplicationUuidGenerator.next();

        application.setAppid(appid);
        application.setOwnerUuid(user.getUuid());
        application.setCreationMethod("connect");
        application.setFramework("");
        application.setAppName(request.getAppName());
        application.setAppUrl(appUrl);
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        application.setOriginalFilename("");
        application.setOriginalFileType("");
        application.setOriginalFileSize("");
        return application;
    }
}
