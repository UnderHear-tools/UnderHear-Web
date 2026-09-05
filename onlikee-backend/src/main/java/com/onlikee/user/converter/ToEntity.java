package com.onlikee.user.converter;

import java.time.LocalDateTime;
import com.onlikee.user.model.entity.User;

public final class ToEntity {

    private ToEntity() {
    }

    public static User toUpdateUser(User user, LocalDateTime lastLoginAt, String lastLoginSource) {
        user.setLastLoginAt(lastLoginAt);
        user.setLastLoginSource(lastLoginSource);
        return user;
    }
}
