package com.underhear.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.dto.response.UserProfileDore;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserProfileMarkdown;

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
    // 公开资料响应在基础用户信息之外，还要带上 Markdown 资料页内容。
    void toUserProfileDoreShouldCopyUserInfoAndMarkdown() {
        User user = user();
        UserProfileMarkdown markdown = new UserProfileMarkdown();
        markdown.setContent("# Hello");

        UserProfileDore userProfileDore = ToDore.toUserProfileDore(user, markdown);

        assertEquals("user-1", userProfileDore.getUuid());
        assertEquals("tester", userProfileDore.getNickname());
        assertEquals("tester@example.com", userProfileDore.getEmail());
        assertEquals("https://avatar/tester.png", userProfileDore.getAvatarUrl());
        assertEquals("bio text", userProfileDore.getBio());
        assertEquals("# Hello", userProfileDore.getMarkdown());
    }

    @Test
    // 没有 Markdown 记录时公开资料响应仍保留用户信息，markdown 字段为空。
    void toUserProfileDoreShouldUseNullMarkdownWhenMissing() {
        UserProfileDore userProfileDore = ToDore.toUserProfileDore(user(), null);

        assertEquals("tester", userProfileDore.getNickname());
        assertEquals(null, userProfileDore.getMarkdown());
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
