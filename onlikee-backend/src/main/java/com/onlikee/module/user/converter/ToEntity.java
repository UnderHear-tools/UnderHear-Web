package com.onlikee.module.user.converter;

import java.time.LocalDateTime;
import com.onlikee.module.user.model.entity.UserEntity;

public final class ToEntity {

    private ToEntity() {
    }

    public static UserEntity toUpdateUserEntity(UserEntity user, LocalDateTime lastLoginAt, String lastLoginSource) {
        user.setLastLoginAt(lastLoginAt);
        user.setLastLoginSource(lastLoginSource);
        return user;
    }
}
