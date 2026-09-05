package com.onlikee.auth.oauth.converter;

import com.onlikee.auth.oauth.model.dto.OAuthPendingSignupDTO;
import com.onlikee.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.auth.oauth.model.entity.UserGiteeEntity;
import com.onlikee.auth.oauth.model.entity.UserGithubEntity;
import com.onlikee.common.util.ShortUuidGenerator;

public final class ToEntity {

    private ToEntity() {
    }

    //github第一次登录时用到
    public static UserGithubEntity toUserGithubEntity(UserGithubDTO userGithubDTO) {
        UserGithubEntity userGithub = new UserGithubEntity();
        userGithub.setUuid(ShortUuidGenerator.next());
        userGithub.setGithubId(userGithubDTO.getGithubId());
        userGithub.setName(userGithubDTO.getName());
        userGithub.setAvatarUrl(userGithubDTO.getAvatarUrl());
        userGithub.setEmail(userGithubDTO.getEmail());
        userGithub.setBio(userGithubDTO.getBio());
        userGithub.setHtmlUrl(userGithubDTO.getHtmlUrl());
        userGithub.setGithubToken(userGithubDTO.getGithubToken());
        return userGithub;
    }

    //github非第一次登录时用到
    public static UserGithubEntity toUpdateUserGithubEntity(UserGithubDTO userGithubDTO) {
        UserGithubEntity userGithub = new UserGithubEntity();
        userGithub.setGithubId(userGithubDTO.getGithubId());
        userGithub.setName(userGithubDTO.getName());
        userGithub.setAvatarUrl(userGithubDTO.getAvatarUrl());
        userGithub.setEmail(userGithubDTO.getEmail());
        userGithub.setBio(userGithubDTO.getBio());
        userGithub.setHtmlUrl(userGithubDTO.getHtmlUrl());
        userGithub.setGithubToken(userGithubDTO.getGithubToken());
        return userGithub;
    }

    //github第一次登录时用到
    public static UserEntity toUserEntity(UserGithubEntity userGithub) {
        UserEntity user = new UserEntity();
        user.setUuid(userGithub.getUuid());
        user.setNickName(userGithub.getName());
        user.setEmail(userGithub.getEmail());
        user.setAvatarUrl(userGithub.getAvatarUrl());
        user.setLastLoginSource("GITHUB_OAUTH");
        return user;
    }

    //gitee第一次登录时用到
    public static UserGiteeEntity toUserGiteeEntity(UserGiteeDTO userGiteeDTO) {
        UserGiteeEntity userGitee = new UserGiteeEntity();
        userGitee.setUuid(ShortUuidGenerator.next());
        userGitee.setGiteeId(userGiteeDTO.getGiteeId());
        userGitee.setName(userGiteeDTO.getName());
        userGitee.setAvatarUrl(userGiteeDTO.getAvatarUrl());
        userGitee.setEmail(userGiteeDTO.getEmail());
        userGitee.setBio(userGiteeDTO.getBio());
        userGitee.setHtmlUrl(userGiteeDTO.getHtmlUrl());
        userGitee.setGiteeToken(userGiteeDTO.getGiteeToken());
        return userGitee;
    }

    //gitee非第一次登录时用到
    public static UserGiteeEntity toUpdateUserGiteeEntity(UserGiteeDTO userGiteeDTO) {
        UserGiteeEntity userGitee = new UserGiteeEntity();
        userGitee.setGiteeId(userGiteeDTO.getGiteeId());
        userGitee.setName(userGiteeDTO.getName());
        userGitee.setAvatarUrl(userGiteeDTO.getAvatarUrl());
        userGitee.setEmail(userGiteeDTO.getEmail());
        userGitee.setBio(userGiteeDTO.getBio());
        userGitee.setHtmlUrl(userGiteeDTO.getHtmlUrl());
        userGitee.setGiteeToken(userGiteeDTO.getGiteeToken());
        return userGitee;
    }

    //gitee第一次登录时用到
    public static UserEntity toUserEntity(UserGiteeEntity userGitee) {
        UserEntity user = new UserEntity();
        user.setUuid(userGitee.getUuid());
        user.setNickName(userGitee.getName());
        user.setEmail(userGitee.getEmail());
        user.setAvatarUrl(userGitee.getAvatarUrl());
        user.setLastLoginSource("GITEE_OAUTH");
        return user;
    }

    public static UserEntity toOAuthSignupUserEntity(OAuthPendingSignupDTO pendingSignup, String uuid, String nickname, String email) {
        UserEntity user = new UserEntity();
        user.setUuid(uuid);
        user.setNickName(nickname);
        user.setEmail(email);
        user.setAvatarUrl(pendingSignup.getAvatarUrl());
        user.setLastLoginSource(toLoginSource(pendingSignup.getProvider()));
        return user;
    }

    public static UserGithubEntity toUserGithubEntity(OAuthPendingSignupDTO pendingSignup, String uuid) {
        UserGithubEntity userGithub = new UserGithubEntity();
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

    public static UserGiteeEntity toUserGiteeEntity(OAuthPendingSignupDTO pendingSignup, String uuid) {
        UserGiteeEntity userGitee = new UserGiteeEntity();
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
}
