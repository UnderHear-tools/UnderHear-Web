package com.underhear.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.underhear.mapper.user.UserMapper;
import com.underhear.pojo.entity.User;
import com.underhear.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(cacheNames = "user:info", key = "#uuid")
    // 根据 UUID 查询用户信息，命中 Redis 缓存时可减少数据库访问。
    public User getUserByUuid(String uuid) {
        return userMapper.getUserByUuid(uuid);
    }

    @Override
    // 根据 GitHub ID 查询用户信息。
    public User getUserByGithubId(Long githubId) {
        if (githubId == null) {
            return null;
        }
        return userMapper.getUserByGithubId(githubId);
    }

    @Override
    // 根据 Gitee ID 查询用户信息。
    public User getUserByGiteeId(Long giteeId) {
        if (giteeId == null) {
            return null;
        }
        return userMapper.getUserByGiteeId(giteeId);
    }

    @Override
    // 更新用户最后登录信息。
    public int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource) {
        if (uuid == null || uuid.isBlank()) {
            return 0;
        }
        return userMapper.updateUserLastLoginByUuid(uuid, lastLoginAt, lastLoginSource);
    }

    @Override
    // 记录一次登录来源。
    public int insertUserLoginRecord(String uuid, String loginSource) {
        if (uuid == null || uuid.isBlank() || loginSource == null || loginSource.isBlank()) {
            return 0;
        }
        return userMapper.insertUserLoginRecord(uuid, loginSource);
    }
}
