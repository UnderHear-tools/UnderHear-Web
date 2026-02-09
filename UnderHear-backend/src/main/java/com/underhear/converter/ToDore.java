package com.underhear.converter;

import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.User;

public final class ToDore {

    private ToDore() {
    }

    public static UserInfoDore toUserInfoDore(User user) {
        UserInfoDore userInfoDore = new UserInfoDore();
        userInfoDore.setUuid(user.getUuid());
        userInfoDore.setNickname(user.getNickName());
        userInfoDore.setAvatarUrl(user.getAvatarUrl());
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
    
}
