package com.underhear.converter;

import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.dto.response.OAuthCallbackDore;
import com.underhear.pojo.dto.response.OAuthCallbackWithTokenDore;
import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;

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

    public static ApplicationCreateNewDore toApplicationCreateNewDore(Application application) {
        ApplicationCreateNewDore applicationCreateNewDore = new ApplicationCreateNewDore();
        applicationCreateNewDore.setAppUrl(application.getAppUrl());
        return applicationCreateNewDore;
    }
    
}
