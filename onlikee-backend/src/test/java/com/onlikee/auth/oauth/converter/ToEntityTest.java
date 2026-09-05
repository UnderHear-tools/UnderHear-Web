package com.onlikee.auth.oauth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.onlikee.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.auth.oauth.model.entity.UserGiteeEntity;
import com.onlikee.auth.oauth.model.entity.UserGithubEntity;

class ToEntityTest {

    @Test
    // GitHub 首次登录转换时应复制第三方资料并生成项目 uuid。
    void toUserGithubEntityShouldCopyFieldsAndGenerateUuid() {
        UserGithubDTO dto = githubDTO();

        UserGithubEntity userGithub = ToEntity.toUserGithubEntity(dto);

        assertNotNull(userGithub.getUuid());
        assertTrue(userGithub.getUuid().matches("\\d{11}"));
        assertEquals(dto.getGithubId(), userGithub.getGithubId());
        assertEquals(dto.getName(), userGithub.getName());
        assertEquals(dto.getAvatarUrl(), userGithub.getAvatarUrl());
        assertEquals(dto.getEmail(), userGithub.getEmail());
        assertEquals(dto.getBio(), userGithub.getBio());
        assertEquals(dto.getHtmlUrl(), userGithub.getHtmlUrl());
        assertEquals(dto.getGithubToken(), userGithub.getGithubToken());
    }

    @Test
    // GitHub 更新对象不应在转换阶段重新生成 uuid。
    void toUpdateUserGithubEntityShouldOnlyCopyUpdatableFields() {
        UserGithubDTO dto = githubDTO();

        UserGithubEntity userGithub = ToEntity.toUpdateUserGithubEntity(dto);

        assertEquals(dto.getGithubId(), userGithub.getGithubId());
        assertEquals(dto.getName(), userGithub.getName());
        assertEquals(dto.getAvatarUrl(), userGithub.getAvatarUrl());
        assertEquals(dto.getEmail(), userGithub.getEmail());
        assertFalse(userGithub.getUuid() != null && !userGithub.getUuid().isBlank());
    }

    @Test
    // Gitee 首次登录转换时应复制第三方资料并生成项目 uuid。
    void toUserGiteeEntityShouldCopyFieldsAndGenerateUuid() {
        UserGiteeDTO dto = giteeDTO();

        UserGiteeEntity userGitee = ToEntity.toUserGiteeEntity(dto);

        assertNotNull(userGitee.getUuid());
        assertTrue(userGitee.getUuid().matches("\\d{11}"));
        assertEquals(dto.getGiteeId(), userGitee.getGiteeId());
        assertEquals(dto.getName(), userGitee.getName());
        assertEquals(dto.getAvatarUrl(), userGitee.getAvatarUrl());
        assertEquals(dto.getEmail(), userGitee.getEmail());
        assertEquals(dto.getBio(), userGitee.getBio());
        assertEquals(dto.getHtmlUrl(), userGitee.getHtmlUrl());
        assertEquals(dto.getGiteeToken(), userGitee.getGiteeToken());
    }

    @Test
    // Gitee 更新对象不应在转换阶段重新生成 uuid。
    void toUpdateUserGiteeEntityShouldOnlyCopyUpdatableFields() {
        UserGiteeDTO dto = giteeDTO();

        UserGiteeEntity userGitee = ToEntity.toUpdateUserGiteeEntity(dto);

        assertEquals(dto.getGiteeId(), userGitee.getGiteeId());
        assertEquals(dto.getName(), userGitee.getName());
        assertEquals(dto.getAvatarUrl(), userGitee.getAvatarUrl());
        assertEquals(dto.getEmail(), userGitee.getEmail());
        assertFalse(userGitee.getUuid() != null && !userGitee.getUuid().isBlank());
    }

    @Test
    // GitHub 第一次登录写入 user 表时应带上登录来源。
    void toUserEntityFromGithubShouldFillCoreUserFields() {
        UserGithubEntity userGithub = new UserGithubEntity();
        userGithub.setUuid("12345678901");
        userGithub.setName("github-user");
        userGithub.setEmail("github@example.com");
        userGithub.setAvatarUrl("https://avatar/github.png");

        UserEntity user = ToEntity.toUserEntity(userGithub);

        assertEquals("12345678901", user.getUuid());
        assertEquals("github-user", user.getNickName());
        assertEquals("github@example.com", user.getEmail());
        assertEquals("https://avatar/github.png", user.getAvatarUrl());
        assertEquals("GITHUB_OAUTH", user.getLastLoginSource());
    }

    @Test
    // Gitee 第一次登录写入 user 表时应带上登录来源。
    void toUserEntityFromGiteeShouldFillCoreUserFields() {
        UserGiteeEntity userGitee = new UserGiteeEntity();
        userGitee.setUuid("12345678901");
        userGitee.setName("gitee-user");
        userGitee.setEmail("gitee@example.com");
        userGitee.setAvatarUrl("https://avatar/gitee.png");

        UserEntity user = ToEntity.toUserEntity(userGitee);

        assertEquals("12345678901", user.getUuid());
        assertEquals("gitee-user", user.getNickName());
        assertEquals("gitee@example.com", user.getEmail());
        assertEquals("https://avatar/gitee.png", user.getAvatarUrl());
        assertEquals("GITEE_OAUTH", user.getLastLoginSource());
    }

    private UserGithubDTO githubDTO() {
        UserGithubDTO dto = new UserGithubDTO();
        dto.setGithubId(1001L);
        dto.setName("github-user");
        dto.setAvatarUrl("https://avatar/github.png");
        dto.setEmail("github@example.com");
        dto.setBio("github bio");
        dto.setHtmlUrl("https://github.com/demo");
        dto.setGithubToken("github-token");
        return dto;
    }

    private UserGiteeDTO giteeDTO() {
        UserGiteeDTO dto = new UserGiteeDTO();
        dto.setGiteeId(2002L);
        dto.setName("gitee-user");
        dto.setAvatarUrl("https://avatar/gitee.png");
        dto.setEmail("gitee@example.com");
        dto.setBio("gitee bio");
        dto.setHtmlUrl("https://gitee.com/demo");
        dto.setGiteeToken("gitee-token");
        return dto;
    }
}
