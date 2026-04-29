package com.underhear.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;

class ToDoreTest {

    @Test
    // 用户信息响应对象应保留基础资料字段。
    void toUserInfoDoreShouldCopyCoreFields() {
        User user = user();

        UserInfoDore userInfoDore = ToDore.toUserInfoDore(user);

        assertEquals("user-1", userInfoDore.getUuid());
        assertEquals("tester", userInfoDore.getNickname());
        assertEquals("tester@example.com", userInfoDore.getEmail());
        assertEquals("https://avatar/tester.png", userInfoDore.getAvatarUrl());
        assertEquals("bio text", userInfoDore.getBio());
        assertEquals("they/them", userInfoDore.getPronoun());
        assertEquals("Shanghai", userInfoDore.getLocation());
        assertEquals("https://github.com/tester", userInfoDore.getSocialAccount0());
        assertEquals("https://gitee.com/tester", userInfoDore.getSocialAccount1());
        assertEquals("https://example.com/tester", userInfoDore.getSocialAccount2());
    }

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

    @Test
    // 应用创建响应对象只需向外暴露应用 URL。
    void toApplicationCreateNewDoreShouldCopyAppUrl() {
        Application application = new Application();
        application.setAppUrl("https://demo.underhear.cn/");

        ApplicationCreateNewDore dore = ToDore.toApplicationCreateNewDore(application);

        assertEquals("https://demo.underhear.cn/", dore.getAppUrl());
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
