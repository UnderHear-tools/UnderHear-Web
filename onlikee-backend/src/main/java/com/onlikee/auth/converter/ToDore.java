package com.onlikee.auth.converter;

import com.onlikee.auth.model.dto.response.UserLoginDore;
import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;
import com.onlikee.user.model.entity.User;

public final class ToDore {

    private ToDore() {
    }

    public static UserLoginWithTokenDore toUserLoginWithTokenDore(User user, String token) {
        UserLoginWithTokenDore userLoginWithTokenDore = new UserLoginWithTokenDore();
        userLoginWithTokenDore.setToken(token);
        String loginSource = user.getLastLoginSource();
        userLoginWithTokenDore.setLoginSource(loginSource);
        userLoginWithTokenDore.setUserInfo(com.onlikee.user.converter.ToDore.toUserInfoDore(user));
        return userLoginWithTokenDore;
    }

    public static UserLoginDore toUserLoginDore(UserLoginWithTokenDore userLoginWithTokenDore) {
        UserLoginDore userLoginDore = new UserLoginDore();
        userLoginDore.setLoginSource(userLoginWithTokenDore.getLoginSource());
        userLoginDore.setUserInfo(userLoginWithTokenDore.getUserInfo());
        return userLoginDore;
    }
}
