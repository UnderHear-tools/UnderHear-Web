package com.onlikee.module.auth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;
import com.onlikee.module.user.model.entity.UserEntity;

class ToDTOTest {

    @Test
    // 内部登录结果转换应包含 token、登录来源和用户信息。
    void toUserLoginWithTokenDTOShouldContainTokenAndUserInfo() {
        UserEntity user = user();

        UserLoginWithTokenDTO userLoginWithTokenDTO = ToDTO.toUserLoginWithTokenDTO(user, "jwt-token");

        assertEquals("jwt-token", userLoginWithTokenDTO.getToken());
        assertEquals("GITHUB_OAUTH", userLoginWithTokenDTO.getLoginSource());
        assertEquals("tester", userLoginWithTokenDTO.getUserInfo().getNickname());
    }

    private UserEntity user() {
        UserEntity user = new UserEntity();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        user.setAvatarUrl("https://avatar/tester.png");
        user.setBio("bio text");
        user.setPronoun("they/them");
        user.setLocation("Shanghai");
        user.setSocialAccount0("https://github.com/tester");
        user.setSocialAccount1("https://gitee.com/tester");
        user.setSocialAccount2("https://example.com/tester");
        user.setLastLoginSource("GITHUB_OAUTH");
        return user;
    }
}
