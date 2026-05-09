package com.underhear.service.user.impl;

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

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.user.UserProfileMapper;
import com.underhear.pojo.dto.request.UserProfileMarkdownDort;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserProfileMarkdown;

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

    private UserProfileMarkdown markdown(String content) {
        UserProfileMarkdown markdown = new UserProfileMarkdown();
        markdown.setUuid("user-1");
        markdown.setContent(content);
        return markdown;
    }
}
