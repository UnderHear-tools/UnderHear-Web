package com.underhear.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.user.UserProfileMapper;
import com.underhear.pojo.entity.User;

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

    private User user() {
        User user = new User();
        user.setUuid("user-1");
        user.setNickName("tester");
        user.setEmail("tester@example.com");
        return user;
    }
}
