package com.onlikee.module.user.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.onlikee.module.user.model.entity.UserEntity;

class ToEntityTest {

    @Test
    // 更新用户登录态时只应覆盖最后登录信息。
    void toUpdateUserEntityShouldApplyLastLoginFields() {
        UserEntity user = new UserEntity();
        LocalDateTime lastLoginAt = LocalDateTime.of(2026, 4, 13, 8, 0);

        UserEntity updatedUser = ToEntity.toUpdateUserEntity(user, lastLoginAt, "GITHUB_OAUTH");

        assertEquals(lastLoginAt, updatedUser.getLastLoginAt());
        assertEquals("GITHUB_OAUTH", updatedUser.getLastLoginSource());
    }
}
