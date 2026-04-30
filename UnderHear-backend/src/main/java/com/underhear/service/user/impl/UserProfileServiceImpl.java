package com.underhear.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.user.UserProfileMapper;
import com.underhear.pojo.entity.User;
import com.underhear.service.user.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    // 公开资料页使用昵称作为稳定入口，查询不到时复用用户不存在业务错误。
    public User getUserByNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        User user = userProfileMapper.getUserByNickname(nickname);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
