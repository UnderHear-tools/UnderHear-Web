package com.underhear.converter;

import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.UserLoginDore;
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

    public static UserLoginDore toUserLoginDore(User user, String token) {
        UserLoginDore userLoginDore = new UserLoginDore();
        userLoginDore.setToken(token);
        String loginSource = user.getLastLoginSource();
        userLoginDore.setLoginSource(loginSource);
        userLoginDore.setUserInfo(toUserInfoDore(user));
        return userLoginDore;
    }
    
}
