package com.onlikee.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.onlikee.pojo.dto.response.ApplicationCreateCollectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateConnectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.dto.response.UserInfoDore;
import com.onlikee.pojo.dto.response.UserLoginDore;
import com.onlikee.pojo.dto.response.UserLoginWithTokenDore;
import com.onlikee.pojo.dto.response.UserProfileDore;
import com.onlikee.pojo.dto.response.UserProfileMarkdownDore;
import com.onlikee.pojo.entity.Application;
import com.onlikee.pojo.entity.User;
import com.onlikee.pojo.entity.UserProfileMarkdown;

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
    // 公开资料响应只复制基础公开资料字段，Markdown 由独立接口返回。
    void toUserProfileDoreShouldCopyPublicUserInfo() {
        User user = user();

        UserProfileDore userProfileDore = ToDore.toUserProfileDore(user);

        assertEquals("user-1", userProfileDore.getUuid());
        assertEquals("tester", userProfileDore.getNickname());
        assertEquals("tester@example.com", userProfileDore.getEmail());
        assertEquals("https://avatar/tester.png", userProfileDore.getAvatarUrl());
        assertEquals("bio text", userProfileDore.getBio());
        assertEquals("they/them", userProfileDore.getPronoun());
        assertEquals("Shanghai", userProfileDore.getLocation());
        assertEquals("https://github.com/tester", userProfileDore.getSocialAccount0());
        assertEquals("https://gitee.com/tester", userProfileDore.getSocialAccount1());
        assertEquals("https://example.com/tester", userProfileDore.getSocialAccount2());
    }

    @Test
    // 公开 Markdown 响应只暴露 markdown 字段。
    void toUserProfileMarkdownDoreShouldCopyMarkdownContent() {
        UserProfileMarkdown markdown = new UserProfileMarkdown();
        markdown.setContent("# Hello");

        UserProfileMarkdownDore userProfileMarkdownDore = ToDore.toUserProfileMarkdownDore(markdown);

        assertEquals("# Hello", userProfileMarkdownDore.getMarkdown());
    }

    @Test
    // 没有 Markdown 记录时保留响应对象，只把 markdown 字段置空。
    void toUserProfileMarkdownDoreShouldUseNullMarkdownWhenMissing() {
        UserProfileMarkdownDore userProfileMarkdownDore = ToDore.toUserProfileMarkdownDore(null);

        assertNull(userProfileMarkdownDore.getMarkdown());
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
        application.setAppUrl("https://demo.onlikee.cn/");

        ApplicationCreateNewDore dore = ToDore.toApplicationCreateNewDore(application);

        assertEquals("https://demo.onlikee.cn/", dore.getAppUrl());
    }

    @Test
    // 接入网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateConnectDoreShouldCopyAppUrl() {
        Application application = new Application();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateConnectDore dore = ToDore.toApplicationCreateConnectDore(application);

        assertEquals("https://www.demo.com", dore.getAppUrl());
    }

    @Test
    // 收录网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateCollectDoreShouldCopyAppUrl() {
        Application application = new Application();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateCollectDore dore = ToDore.toApplicationCreateCollectDore(application);

        assertEquals("https://www.demo.com", dore.getAppUrl());
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
