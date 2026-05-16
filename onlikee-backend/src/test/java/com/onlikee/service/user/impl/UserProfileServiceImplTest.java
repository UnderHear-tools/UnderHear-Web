package com.onlikee.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.mapper.user.UserProfileMapper;
import com.onlikee.pojo.dto.request.UserProfileDort;
import com.onlikee.pojo.dto.request.UserProfileMarkdownDort;
import com.onlikee.pojo.entity.User;
import com.onlikee.pojo.entity.UserProfileMarkdown;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    // 根据昵称查询成功时应直接返回 profile mapper 结果。
    void getUserByNicknameShouldReturnUserWhenFound() {
        User user = user();
        when(userProfileMapper.getUserByNickname("tester")).thenReturn(user);

        User result = userProfileService.getUserByNickname("tester");

        assertSame(user, result);
    }

    @Test
    // 昵称为空时应按内部错误处理。
    void getUserByNicknameShouldRejectBlankNickname() {
        BizException exception = assertThrows(BizException.class, () -> userProfileService.getUserByNickname(" "));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // 根据昵称查询不到用户时应返回 USER_NOT_FOUND。
    void getUserByNicknameShouldThrowWhenUserMissing() {
        when(userProfileMapper.getUserByNickname("missing")).thenReturn(null);

        BizException exception = assertThrows(BizException.class, () -> userProfileService.getUserByNickname("missing"));

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    // 查询 Markdown 时直接按 uuid 读取资料页扩展表。
    void getMarkdownByUuidShouldReturnMapperResult() {
        UserProfileMarkdown markdown = markdown("# Hello");
        when(userProfileMapper.getMarkdownByUuid("user-1")).thenReturn(markdown);

        UserProfileMarkdown result = userProfileService.getMarkdownByUuid("user-1");

        assertSame(markdown, result);
    }

    @Test
    // Markdown 不存在时返回 null，公开资料接口会将其表现为 markdown: null。
    void getMarkdownByUuidShouldReturnNullWhenMissing() {
        when(userProfileMapper.getMarkdownByUuid("user-1")).thenReturn(null);

        UserProfileMarkdown result = userProfileService.getMarkdownByUuid("user-1");

        assertEquals(null, result);
    }

    @Test
    // 保存 Markdown 时只使用登录态用户 uuid，并允许空字符串清空内容。
    void saveCurrentUserMarkdownShouldUpsertByCurrentUserUuid() {
        UserProfileMarkdownDort request = request("# Hello");
        when(userProfileMapper.upsertMarkdown("user-1", "# Hello")).thenReturn(1);

        userProfileService.saveCurrentUserMarkdown(user(), request);

        verify(userProfileMapper).upsertMarkdown("user-1", "# Hello");
    }

    @Test
    // 写入失败时按内部错误处理，避免调用方误以为资料页已保存。
    void saveCurrentUserMarkdownShouldThrowWhenUpsertFails() {
        UserProfileMarkdownDort request = request("");
        when(userProfileMapper.upsertMarkdown("user-1", "")).thenReturn(0);

        BizException exception = assertThrows(BizException.class,
                () -> userProfileService.saveCurrentUserMarkdown(user(), request));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // 保存基础资料时只使用登录态用户 uuid，并返回同步后的用户对象。
    void saveCurrentUserProfileShouldUpdateByCurrentUserUuid() {
        UserProfileDort request = profileRequest(" updated bio ", " they/them ", " Hangzhou ",
                " https://github.com/tester ", " https://gitee.com/tester ", " https://example.com/tester ");
        when(userProfileMapper.updateCurrentUserProfile(
                "user-1",
                "updated bio",
                "they/them",
                "Hangzhou",
                "https://github.com/tester",
                "https://gitee.com/tester",
                "https://example.com/tester")).thenReturn(1);

        User result = userProfileService.saveCurrentUserProfile(user(), request);

        assertEquals("updated bio", result.getBio());
        assertEquals("they/them", result.getPronoun());
        assertEquals("Hangzhou", result.getLocation());
        assertEquals("https://github.com/tester", result.getSocialAccount0());
        assertEquals("https://gitee.com/tester", result.getSocialAccount1());
        assertEquals("https://example.com/tester", result.getSocialAccount2());
    }

    @Test
    // 空字符串资料字段应统一转为 null，方便公开资料展示时按空值处理。
    void saveCurrentUserProfileShouldNormalizeBlankFieldsToNull() {
        UserProfileDort request = profileRequest(" ", "", null, "   ", null, "");
        when(userProfileMapper.updateCurrentUserProfile("user-1", null, null, null, null, null, null)).thenReturn(1);

        User result = userProfileService.saveCurrentUserProfile(user(), request);

        assertEquals(null, result.getBio());
        assertEquals(null, result.getPronoun());
        assertEquals(null, result.getLocation());
        assertEquals(null, result.getSocialAccount0());
        assertEquals(null, result.getSocialAccount1());
        assertEquals(null, result.getSocialAccount2());
    }

    @Test
    // 基础资料更新失败时按内部错误处理，避免调用方误以为资料已保存。
    void saveCurrentUserProfileShouldThrowWhenUpdateFails() {
        UserProfileDort request = profileRequest("bio", null, null, null, null, null);
        when(userProfileMapper.updateCurrentUserProfile("user-1", "bio", null, null, null, null, null)).thenReturn(0);

        BizException exception = assertThrows(BizException.class,
                () -> userProfileService.saveCurrentUserProfile(user(), request));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        return user;
    }

    private UserProfileMarkdownDort request(String content) {
        UserProfileMarkdownDort request = new UserProfileMarkdownDort();
        request.setContent(content);
        return request;
    }

    private UserProfileDort profileRequest(String bio,
                                           String pronoun,
                                           String location,
                                           String socialAccount0,
                                           String socialAccount1,
                                           String socialAccount2) {
        UserProfileDort request = new UserProfileDort();
        request.setBio(bio);
        request.setPronoun(pronoun);
        request.setLocation(location);
        request.setSocialAccount0(socialAccount0);
        request.setSocialAccount1(socialAccount1);
        request.setSocialAccount2(socialAccount2);
        return request;
    }

    private UserProfileMarkdown markdown(String content) {
        UserProfileMarkdown markdown = new UserProfileMarkdown();
        markdown.setUuid("user-1");
        markdown.setContent(content);
        return markdown;
    }
}
