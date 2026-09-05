package com.onlikee.user.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.onlikee.user.model.entity.User;

class ToEntityTest {

    @Test
    // 更新用户登录态时只应覆盖最后登录信息。
    void toUpdateUserShouldApplyLastLoginFields() {
        User user = new User();
        LocalDateTime lastLoginAt = LocalDateTime.of(2026, 4, 13, 8, 0);

        User updatedUser = ToEntity.toUpdateUser(user, lastLoginAt, "GITHUB_OAUTH");

        assertEquals(lastLoginAt, updatedUser.getLastLoginAt());
        assertEquals("GITHUB_OAUTH", updatedUser.getLastLoginSource());
    }
}
