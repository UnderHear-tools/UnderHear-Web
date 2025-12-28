package com.underhear.converter;

import com.underhear.pojo.entity.User;
import com.underhear.pojo.dto.response.UserLoginDore;

public final class EntityToDore {

    private EntityToDore() {
    }

    public static UserLoginDore toUserDore(User user) {
        UserLoginDore userLoginDore = new UserLoginDore();
        userLoginDore.setUuid(user.getUuid());
        userLoginDore.setNickname(user.getNickName());
        userLoginDore.setAvatarUrl(user.getAvatarUrl());
        userLoginDore.setLastLoginSource(user.getLastLoginSource());
        return userLoginDore;
    }
    
}
