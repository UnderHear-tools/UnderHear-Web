package com.onlikee.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.mapper.user.UserMapper;
import com.onlikee.pojo.entity.User;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    // 根据 uuid 查询成功时应直接返回 mapper 结果。
    void getUserByUuidShouldReturnUserWhenFound() {
        User user = user();
        when(userMapper.getUserByUuid("user-1")).thenReturn(user);

        User result = userService.getUserByUuid("user-1");

        assertSame(user, result);
    }

    @Test
    // uuid 为空时应按内部错误处理。
    void getUserByUuidShouldRejectBlankUuid() {
        BizException exception = assertThrows(BizException.class, () -> userService.getUserByUuid(" "));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // 根据 uuid 查询不到用户时应返回 USER_NOT_FOUND。
    void getUserByUuidShouldThrowWhenUserMissing() {
        when(userMapper.getUserByUuid("user-1")).thenReturn(null);

        BizException exception = assertThrows(BizException.class, () -> userService.getUserByUuid("user-1"));

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    // GitHub 关联用户存在时应返回查询结果。
    void getUserByGithubIdShouldReturnUserWhenFound() {
        User user = user();
        when(userMapper.getUserByGithubId(1001L)).thenReturn(user);

        User result = userService.getUserByGithubId(1001L);

        assertSame(user, result);
    }

    @Test
    // GitHub ID 为空时应拒绝查询。
    void getUserByGithubIdShouldRejectNullId() {
        BizException exception = assertThrows(BizException.class, () -> userService.getUserByGithubId(null));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // Gitee 关联用户查询不到时应返回 USER_NOT_FOUND。
    void getUserByGiteeIdShouldThrowWhenUserMissing() {
        when(userMapper.getUserByGiteeId(2002L)).thenReturn(null);

        BizException exception = assertThrows(BizException.class, () -> userService.getUserByGiteeId(2002L));

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    // 更新最后登录信息成功时应只调用一次 mapper。
    void updateUserLastLoginByUuidShouldSucceedWhenAffectedRowsIsOne() {
        LocalDateTime lastLoginAt = LocalDateTime.of(2026, 4, 13, 9, 0);
        when(userMapper.updateUserLastLoginByUuid("user-1", lastLoginAt, "GITHUB_OAUTH")).thenReturn(1);

        userService.updateUserLastLoginByUuid("user-1", lastLoginAt, "GITHUB_OAUTH");

        verify(userMapper).updateUserLastLoginByUuid("user-1", lastLoginAt, "GITHUB_OAUTH");
    }

    @Test
    // 更新最后登录信息时影响行数不为 1 应视为失败。
    void updateUserLastLoginByUuidShouldThrowWhenAffectedRowsUnexpected() {
        LocalDateTime lastLoginAt = LocalDateTime.of(2026, 4, 13, 9, 0);
        when(userMapper.updateUserLastLoginByUuid("user-1", lastLoginAt, "GITHUB_OAUTH")).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> userService.updateUserLastLoginByUuid("user-1", lastLoginAt, "GITHUB_OAUTH"));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // 登录日志参数不完整时不应继续落库。
    void insertUserLoginRecordShouldRejectInvalidArguments() {
        BizException exception = assertThrows(
                BizException.class,
                () -> userService.insertUserLoginRecord("user-1", " "));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    @Test
    // 登录日志写入行数异常时应按失败处理。
    void insertUserLoginRecordShouldThrowWhenAffectedRowsUnexpected() {
        when(userMapper.insertUserLoginRecord("user-1", "GITHUB_OAUTH")).thenReturn(0);

        BizException exception = assertThrows(
                BizException.class,
                () -> userService.insertUserLoginRecord("user-1", "GITHUB_OAUTH"));

        assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), exception.getCode());
    }

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        return user;
    }
}
