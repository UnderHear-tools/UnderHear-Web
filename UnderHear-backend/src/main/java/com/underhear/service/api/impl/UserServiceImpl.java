package com.underhear.service.api.impl;

import org.springframework.stereotype.Service;

import com.underhear.mapper.api.UserMapper;
import com.underhear.pojo.entity.User;
import com.underhear.service.api.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByGithubId(Long githubId) {
        if (githubId == null) {
            return null;
        }
        return userMapper.getUserByGithubId(githubId);
    }

    @Override
    public int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource) {
        if (uuid == null || uuid.isBlank()) {
            return 0;
        }
        return userMapper.updateUserLastLoginByUuid(uuid, lastLoginAt, lastLoginSource);
    }
}
