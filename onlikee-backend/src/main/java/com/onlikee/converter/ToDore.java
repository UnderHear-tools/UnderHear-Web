package com.onlikee.converter;

import com.onlikee.pojo.dto.response.ApplicationCreateCollectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateConnectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.dto.response.OAuthCallbackDore;
import com.onlikee.pojo.dto.response.OAuthCallbackWithTokenDore;
import com.onlikee.pojo.dto.response.UserInfoDore;
import com.onlikee.pojo.dto.response.UserLoginDore;
import com.onlikee.pojo.dto.response.UserLoginWithTokenDore;
import com.onlikee.pojo.dto.response.UserProfileDore;
import com.onlikee.pojo.dto.response.UserProfileMarkdownDore;
import com.onlikee.pojo.entity.ApplicationCollect;
import com.onlikee.pojo.entity.ApplicationConnect;
import com.onlikee.pojo.entity.ApplicationNew;
import com.onlikee.pojo.entity.User;
import com.onlikee.pojo.entity.UserProfileMarkdown;
import com.onlikee.util.UrlUtils;

public final class ToDore {

    private ToDore() {
    }

    public static UserInfoDore toUserInfoDore(User user) {
        UserInfoDore userInfoDore = new UserInfoDore();
        userInfoDore.setUuid(user.getUuid());
        userInfoDore.setNickname(user.getNickName());
        userInfoDore.setEmail(user.getEmail());
        userInfoDore.setAvatarUrl(user.getAvatarUrl());
        userInfoDore.setBio(user.getBio());
        userInfoDore.setPronoun(user.getPronoun());
        userInfoDore.setLocation(user.getLocation());
        userInfoDore.setSocialAccount0(user.getSocialAccount0());
        userInfoDore.setSocialAccount1(user.getSocialAccount1());
        userInfoDore.setSocialAccount2(user.getSocialAccount2());
        return userInfoDore;
    }

    public static UserProfileDore toUserProfileDore(User user) {
        UserProfileDore userProfileDore = new UserProfileDore();
        userProfileDore.setUuid(user.getUuid());
        userProfileDore.setNickname(user.getNickName());
        userProfileDore.setEmail(user.getEmail());
        userProfileDore.setAvatarUrl(user.getAvatarUrl());
        userProfileDore.setBio(user.getBio());
        userProfileDore.setPronoun(user.getPronoun());
        userProfileDore.setLocation(user.getLocation());
        userProfileDore.setSocialAccount0(user.getSocialAccount0());
        userProfileDore.setSocialAccount1(user.getSocialAccount1());
        userProfileDore.setSocialAccount2(user.getSocialAccount2());
        return userProfileDore;
    }

    public static UserProfileMarkdownDore toUserProfileMarkdownDore(UserProfileMarkdown markdown) {
        UserProfileMarkdownDore userProfileMarkdownDore = new UserProfileMarkdownDore();
        userProfileMarkdownDore.setMarkdown(markdown == null ? null : markdown.getContent());
        return userProfileMarkdownDore;
    }

    public static UserLoginWithTokenDore toUserLoginWithTokenDore(User user, String token) {
        UserLoginWithTokenDore userLoginWithTokenDore = new UserLoginWithTokenDore();
        userLoginWithTokenDore.setToken(token);
        String loginSource = user.getLastLoginSource();
        userLoginWithTokenDore.setLoginSource(loginSource);
        userLoginWithTokenDore.setUserInfo(toUserInfoDore(user));
        return userLoginWithTokenDore;
    }

    public static UserLoginDore toUserLoginDore(UserLoginWithTokenDore userLoginWithTokenDore) {
        UserLoginDore userLoginDore = new UserLoginDore();
        userLoginDore.setLoginSource(userLoginWithTokenDore.getLoginSource());
        userLoginDore.setUserInfo(userLoginWithTokenDore.getUserInfo());
        return userLoginDore;
    }

    public static OAuthCallbackDore toOAuthCallbackDore(OAuthCallbackWithTokenDore callbackWithToken) {
        OAuthCallbackDore callback = new OAuthCallbackDore();
        callback.setStatus(callbackWithToken.getStatus());
        callback.setLoginSource(callbackWithToken.getLoginSource());
        callback.setUserInfo(callbackWithToken.getUserInfo());
        callback.setPendingSignupToken(callbackWithToken.getPendingSignupToken());
        callback.setProvider(callbackWithToken.getProvider());
        callback.setAvatarUrl(callbackWithToken.getAvatarUrl());
        callback.setSuggestedNickname(callbackWithToken.getSuggestedNickname());
        callback.setEmail(callbackWithToken.getEmail());
        return callback;
    }

    public static ApplicationCreateNewDore toApplicationCreateNewDore(ApplicationNew application) {
        ApplicationCreateNewDore applicationCreateNewDore = new ApplicationCreateNewDore();
        applicationCreateNewDore.setAppUrl(UrlUtils.buildAppUrl(application.getAppSubDomain()));
        return applicationCreateNewDore;
    }

    public static ApplicationCreateConnectDore toApplicationCreateConnectDore(ApplicationConnect application) {
        ApplicationCreateConnectDore applicationCreateConnectDore = new ApplicationCreateConnectDore();
        applicationCreateConnectDore.setAppUrl(application.getAppUrl());
        return applicationCreateConnectDore;
    }

    public static ApplicationCreateCollectDore toApplicationCreateCollectDore(ApplicationCollect application) {
        ApplicationCreateCollectDore applicationCreateCollectDore = new ApplicationCreateCollectDore();
        applicationCreateCollectDore.setAppUrl(application.getAppUrl());
        return applicationCreateCollectDore;
    }
    
}
