package com.onlikee.auth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import com.onlikee.user.model.dto.response.UserInfoDore;
import com.onlikee.auth.model.dto.response.UserLoginDore;
import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;
import com.onlikee.user.model.entity.User;

class ToDoreTest {

    @Test
    // 登录响应转换应包含 token、登录来源和用户信息。
    void toUserLoginWithTokenDoreShouldContainTokenAndUserInfo() {
        User user = user();

        UserLoginWithTokenDore userLoginWithTokenDore = ToDore.toUserLoginWithTokenDore(user, "jwt-token");

        assertEquals("jwt-token", userLoginWithTokenDore.getToken());
        assertEquals("GITHUB_OAUTH", userLoginWithTokenDore.getLoginSource());
        assertEquals("tester", userLoginWithTokenDore.getUserInfo().getNickname());
    }

    @Test
    // 对外登录响应应移除 token，但保留登录来源和用户信息。
    void toUserLoginDoreShouldDropTokenAndKeepUserInfo() {
        UserLoginWithTokenDore withToken = new UserLoginWithTokenDore();
        UserInfoDore userInfoDore = new UserInfoDore();
        userInfoDore.setUuid("user-1");
        withToken.setLoginSource("GITEE_OAUTH");
        withToken.setUserInfo(userInfoDore);

        UserLoginDore userLoginDore = ToDore.toUserLoginDore(withToken);

        assertEquals("GITEE_OAUTH", userLoginDore.getLoginSource());
        assertSame(userInfoDore, userLoginDore.getUserInfo());
    }

    private User user() {
        User user = new User();
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
