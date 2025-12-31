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
    // 传 GitHub ID 进来，空的就直接返回
    public User getUserByGithubId(Long githubId) {
        if (githubId == null) {
            return null;
        }
        return userMapper.getUserByGithubId(githubId);
    }

    @Override
    // 传 Gitee ID 进来，空的就直接返回
    public User getUserByGiteeId(Long giteeId) {
        if (giteeId == null) {
            return null;
        }
        return userMapper.getUserByGiteeId(giteeId);
    }

    @Override
    // 更新最后登录时间和来源，uuid 为空就不动
    public int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource) {
        if (uuid == null || uuid.isBlank()) {
            return 0;
        }
        return userMapper.updateUserLastLoginByUuid(uuid, lastLoginAt, lastLoginSource);
    }

    @Override
    // 插入一条登录记录，参数不全就不写
    public int insertUserLoginRecord(String uuid, String loginSource) {
        if (uuid == null || uuid.isBlank() || loginSource == null || loginSource.isBlank()) {
            return 0;
        }
        return userMapper.insertUserLoginRecord(uuid, loginSource);
    }
}
