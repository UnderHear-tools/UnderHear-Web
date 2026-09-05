package com.onlikee.auth.oauth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.onlikee.auth.oauth.model.dto.request.UserGiteeDort;
import com.onlikee.auth.oauth.model.dto.request.UserGithubDort;
import com.onlikee.user.model.entity.User;
import com.onlikee.auth.oauth.model.entity.UserGitee;
import com.onlikee.auth.oauth.model.entity.UserGithub;

class ToEntityTest {

    @Test
    // GitHub 首次登录转换时应复制第三方资料并生成项目 uuid。
    void toUserGithubShouldCopyFieldsAndGenerateUuid() {
        UserGithubDort dort = githubDort();

        UserGithub userGithub = ToEntity.toUserGithub(dort);

        assertNotNull(userGithub.getUuid());
        assertTrue(userGithub.getUuid().matches("\\d{11}"));
        assertEquals(dort.getGithubId(), userGithub.getGithubId());
        assertEquals(dort.getName(), userGithub.getName());
        assertEquals(dort.getAvatarUrl(), userGithub.getAvatarUrl());
        assertEquals(dort.getEmail(), userGithub.getEmail());
        assertEquals(dort.getBio(), userGithub.getBio());
        assertEquals(dort.getHtmlUrl(), userGithub.getHtmlUrl());
        assertEquals(dort.getGithubToken(), userGithub.getGithubToken());
    }

    @Test
    // GitHub 更新对象不应在转换阶段重新生成 uuid。
    void toUpdateUserGithubShouldOnlyCopyUpdatableFields() {
        UserGithubDort dort = githubDort();

        UserGithub userGithub = ToEntity.toUpdateUserGithub(dort);

        assertEquals(dort.getGithubId(), userGithub.getGithubId());
        assertEquals(dort.getName(), userGithub.getName());
        assertEquals(dort.getAvatarUrl(), userGithub.getAvatarUrl());
        assertEquals(dort.getEmail(), userGithub.getEmail());
        assertFalse(userGithub.getUuid() != null && !userGithub.getUuid().isBlank());
    }

    @Test
    // Gitee 首次登录转换时应复制第三方资料并生成项目 uuid。
    void toUserGiteeShouldCopyFieldsAndGenerateUuid() {
        UserGiteeDort dort = giteeDort();

        UserGitee userGitee = ToEntity.toUserGitee(dort);

        assertNotNull(userGitee.getUuid());
        assertTrue(userGitee.getUuid().matches("\\d{11}"));
        assertEquals(dort.getGiteeId(), userGitee.getGiteeId());
        assertEquals(dort.getName(), userGitee.getName());
        assertEquals(dort.getAvatarUrl(), userGitee.getAvatarUrl());
        assertEquals(dort.getEmail(), userGitee.getEmail());
        assertEquals(dort.getBio(), userGitee.getBio());
        assertEquals(dort.getHtmlUrl(), userGitee.getHtmlUrl());
        assertEquals(dort.getGiteeToken(), userGitee.getGiteeToken());
    }

    @Test
    // Gitee 更新对象不应在转换阶段重新生成 uuid。
    void toUpdateUserGiteeShouldOnlyCopyUpdatableFields() {
        UserGiteeDort dort = giteeDort();

        UserGitee userGitee = ToEntity.toUpdateUserGitee(dort);

        assertEquals(dort.getGiteeId(), userGitee.getGiteeId());
        assertEquals(dort.getName(), userGitee.getName());
        assertEquals(dort.getAvatarUrl(), userGitee.getAvatarUrl());
        assertEquals(dort.getEmail(), userGitee.getEmail());
        assertFalse(userGitee.getUuid() != null && !userGitee.getUuid().isBlank());
    }

    @Test
    // GitHub 第一次登录写入 user 表时应带上登录来源。
    void toUserFromGithubShouldFillCoreUserFields() {
        UserGithub userGithub = new UserGithub();
        userGithub.setUuid("12345678901");
        userGithub.setName("github-user");
        userGithub.setEmail("github@example.com");
        userGithub.setAvatarUrl("https://avatar/github.png");

        User user = ToEntity.toUser(userGithub);

        assertEquals("12345678901", user.getUuid());
        assertEquals("github-user", user.getNickName());
        assertEquals("github@example.com", user.getEmail());
        assertEquals("https://avatar/github.png", user.getAvatarUrl());
        assertEquals("GITHUB_OAUTH", user.getLastLoginSource());
    }

    @Test
    // Gitee 第一次登录写入 user 表时应带上登录来源。
    void toUserFromGiteeShouldFillCoreUserFields() {
        UserGitee userGitee = new UserGitee();
        userGitee.setUuid("12345678901");
        userGitee.setName("gitee-user");
        userGitee.setEmail("gitee@example.com");
        userGitee.setAvatarUrl("https://avatar/gitee.png");

        User user = ToEntity.toUser(userGitee);

        assertEquals("12345678901", user.getUuid());
        assertEquals("gitee-user", user.getNickName());
        assertEquals("gitee@example.com", user.getEmail());
        assertEquals("https://avatar/gitee.png", user.getAvatarUrl());
        assertEquals("GITEE_OAUTH", user.getLastLoginSource());
    }

    private UserGithubDort githubDort() {
        UserGithubDort dort = new UserGithubDort();
        dort.setGithubId(1001L);
        dort.setName("github-user");
        dort.setAvatarUrl("https://avatar/github.png");
        dort.setEmail("github@example.com");
        dort.setBio("github bio");
        dort.setHtmlUrl("https://github.com/demo");
        dort.setGithubToken("github-token");
        return dort;
    }

    private UserGiteeDort giteeDort() {
        UserGiteeDort dort = new UserGiteeDort();
        dort.setGiteeId(2002L);
        dort.setName("gitee-user");
        dort.setAvatarUrl("https://avatar/gitee.png");
        dort.setEmail("gitee@example.com");
        dort.setBio("gitee bio");
        dort.setHtmlUrl("https://gitee.com/demo");
        dort.setGiteeToken("gitee-token");
        return dort;
    }
}
