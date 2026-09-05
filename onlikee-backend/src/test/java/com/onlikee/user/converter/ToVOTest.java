package com.onlikee.user.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import com.onlikee.user.model.dto.UserInfoDTO;
import com.onlikee.user.model.vo.UserInfoVO;
import com.onlikee.user.model.vo.UserProfileVO;
import com.onlikee.user.model.vo.UserProfileMarkdownVO;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.user.model.entity.UserProfileMarkdownEntity;

class ToVOTest {

    @Test
    // 用户信息响应对象应保留基础资料字段。
    void toUserInfoVOShouldCopyCoreFields() {
        UserEntity user = user();

        UserInfoVO userInfoVO = ToVO.toUserInfoVO(user);

        assertEquals("user-1", userInfoVO.getUuid());
        assertEquals("tester", userInfoVO.getNickname());
        assertEquals("tester@example.com", userInfoVO.getEmail());
        assertEquals("https://avatar/tester.png", userInfoVO.getAvatarUrl());
        assertEquals("bio text", userInfoVO.getBio());
        assertEquals("they/them", userInfoVO.getPronoun());
        assertEquals("Shanghai", userInfoVO.getLocation());
        assertEquals("https://github.com/tester", userInfoVO.getSocialAccount0());
        assertEquals("https://gitee.com/tester", userInfoVO.getSocialAccount1());
        assertEquals("https://example.com/tester", userInfoVO.getSocialAccount2());
    }

    @Test
    // 服务间传输后转换为展示对象，所有用户资料字段都应保留。
    void toUserInfoVOShouldPreserveTransferredUserData() {
        UserInfoDTO user = ToDTO.toUserInfoDTO(user());

        UserInfoVO userInfoVO = ToVO.toUserInfoVO(user);

        assertEquals("user-1", userInfoVO.getUuid());
        assertEquals("tester", userInfoVO.getNickname());
        assertEquals("tester@example.com", userInfoVO.getEmail());
        assertEquals("https://avatar/tester.png", userInfoVO.getAvatarUrl());
        assertEquals("bio text", userInfoVO.getBio());
        assertEquals("they/them", userInfoVO.getPronoun());
        assertEquals("Shanghai", userInfoVO.getLocation());
        assertEquals("https://github.com/tester", userInfoVO.getSocialAccount0());
        assertEquals("https://gitee.com/tester", userInfoVO.getSocialAccount1());
        assertEquals("https://example.com/tester", userInfoVO.getSocialAccount2());
    }

    @Test
    // 公开资料响应只复制基础公开资料字段，Markdown 由独立接口返回。
    void toUserProfileVOShouldCopyPublicUserInfo() {
        UserEntity user = user();

        UserProfileVO userProfileVO = ToVO.toUserProfileVO(user);

        assertEquals("user-1", userProfileVO.getUuid());
        assertEquals("tester", userProfileVO.getNickname());
        assertEquals("tester@example.com", userProfileVO.getEmail());
        assertEquals("https://avatar/tester.png", userProfileVO.getAvatarUrl());
        assertEquals("bio text", userProfileVO.getBio());
        assertEquals("they/them", userProfileVO.getPronoun());
        assertEquals("Shanghai", userProfileVO.getLocation());
        assertEquals("https://github.com/tester", userProfileVO.getSocialAccount0());
        assertEquals("https://gitee.com/tester", userProfileVO.getSocialAccount1());
        assertEquals("https://example.com/tester", userProfileVO.getSocialAccount2());
    }

    @Test
    // 公开 Markdown 响应只暴露 markdown 字段。
    void toUserProfileMarkdownVOShouldCopyMarkdownContent() {
        UserProfileMarkdownEntity markdown = new UserProfileMarkdownEntity();
        markdown.setContent("# Hello");

        UserProfileMarkdownVO userProfileMarkdownVO = ToVO.toUserProfileMarkdownVO(markdown);

        assertEquals("# Hello", userProfileMarkdownVO.getMarkdown());
    }

    @Test
    // 没有 Markdown 记录时保留响应对象，只把 markdown 字段置空。
    void toUserProfileMarkdownVOShouldUseNullMarkdownWhenMissing() {
        UserProfileMarkdownVO userProfileMarkdownVO = ToVO.toUserProfileMarkdownVO(null);

        assertNull(userProfileMarkdownVO.getMarkdown());
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
