package com.onlikee.module.auth.oauth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSONObject;
import com.onlikee.module.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.module.auth.oauth.model.dto.UserGithubDTO;
import me.zhyd.oauth.model.AuthToken;

class ToDTOTest {

    @Test
    // GitHub 原始用户信息应被转换成项目内部请求对象。
    void toUserGithubDTOShouldExtractFieldsFromRawUserInfo() {
        JSONObject rawUserInfo = rawGithubUserInfo();
        AuthToken token = new AuthToken();
        token.setAccessToken("github-token");

        UserGithubDTO dto = ToDTO.toUserGithubDTO(rawUserInfo, token);

        assertEquals(1001L, dto.getGithubId());
        assertEquals("github-user", dto.getName());
        assertEquals("https://avatar/github.png", dto.getAvatarUrl());
        assertEquals("github@example.com", dto.getEmail());
        assertEquals("github bio", dto.getBio());
        assertEquals("https://github.com/demo", dto.getHtmlUrl());
        assertEquals("github-token", dto.getGithubToken());
    }

    @Test
    // Gitee 原始用户信息应被转换成项目内部请求对象。
    void toUserGiteeDTOShouldExtractFieldsFromRawUserInfo() {
        JSONObject rawUserInfo = rawGiteeUserInfo();
        AuthToken token = new AuthToken();
        token.setAccessToken("gitee-token");

        UserGiteeDTO dto = ToDTO.toUserGiteeDTO(rawUserInfo, token);

        assertEquals(2002L, dto.getGiteeId());
        assertEquals("gitee-user", dto.getName());
        assertEquals("https://avatar/gitee.png", dto.getAvatarUrl());
        assertEquals("gitee@example.com", dto.getEmail());
        assertEquals("gitee bio", dto.getBio());
        assertEquals("https://gitee.com/demo", dto.getHtmlUrl());
        assertEquals("gitee-token", dto.getGiteeToken());
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
