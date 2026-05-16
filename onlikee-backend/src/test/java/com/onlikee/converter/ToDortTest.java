package com.onlikee.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSONObject;
import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.request.UserGithubDort;

import me.zhyd.oauth.model.AuthToken;

class ToDortTest {

    @Test
    // GitHub 原始用户信息应被转换成项目内部请求对象。
    void toUserGithubDortShouldExtractFieldsFromRawUserInfo() {
        JSONObject rawUserInfo = rawGithubUserInfo();
        AuthToken token = new AuthToken();
        token.setAccessToken("github-token");

        UserGithubDort dort = ToDort.toUserGithubDort(rawUserInfo, token);

        assertEquals(1001L, dort.getGithubId());
        assertEquals("github-user", dort.getName());
        assertEquals("https://avatar/github.png", dort.getAvatarUrl());
        assertEquals("github@example.com", dort.getEmail());
        assertEquals("github bio", dort.getBio());
        assertEquals("https://github.com/demo", dort.getHtmlUrl());
        assertEquals("github-token", dort.getGithubToken());
    }

    @Test
    // Gitee 原始用户信息应被转换成项目内部请求对象。
    void toUserGiteeDortShouldExtractFieldsFromRawUserInfo() {
        JSONObject rawUserInfo = rawGiteeUserInfo();
        AuthToken token = new AuthToken();
        token.setAccessToken("gitee-token");

        UserGiteeDort dort = ToDort.toUserGiteeDort(rawUserInfo, token);

        assertEquals(2002L, dort.getGiteeId());
        assertEquals("gitee-user", dort.getName());
        assertEquals("https://avatar/gitee.png", dort.getAvatarUrl());
        assertEquals("gitee@example.com", dort.getEmail());
        assertEquals("gitee bio", dort.getBio());
        assertEquals("https://gitee.com/demo", dort.getHtmlUrl());
        assertEquals("gitee-token", dort.getGiteeToken());
    }

    private JSONObject rawGithubUserInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1001L);
        jsonObject.put("name", "github-user");
        jsonObject.put("avatar_url", "https://avatar/github.png");
        jsonObject.put("email", "github@example.com");
        jsonObject.put("bio", "github bio");
        jsonObject.put("html_url", "https://github.com/demo");
        return jsonObject;
    }

    private JSONObject rawGiteeUserInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 2002L);
        jsonObject.put("name", "gitee-user");
        jsonObject.put("avatar_url", "https://avatar/gitee.png");
        jsonObject.put("email", "gitee@example.com");
        jsonObject.put("bio", "gitee bio");
        jsonObject.put("html_url", "https://gitee.com/demo");
        return jsonObject;
    }
}
