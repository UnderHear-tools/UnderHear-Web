package com.onlikee.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.user.mapper.UserMapper;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(cacheNames = "user:info", key = "#uuid")
    // 根据 UUID 查询用户信息，命中 Redis 缓存时可减少数据库访问。
    public UserEntity getUserByUuid(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        UserEntity user = userMapper.getUserByUuid(uuid);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    // 根据 GitHub ID 查询用户信息。
    public UserEntity getUserByGithubId(Long githubId) {
        if (githubId == null) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        UserEntity user = userMapper.getUserByGithubId(githubId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    // 根据 Gitee ID 查询用户信息。
    public UserEntity getUserByGiteeId(Long giteeId) {
        if (giteeId == null) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        UserEntity user = userMapper.getUserByGiteeId(giteeId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    // 更新用户最后登录信息。
    public void updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource) {
        if (uuid == null || uuid.isBlank() || lastLoginAt == null || lastLoginSource == null || lastLoginSource.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        int affectedRows = userMapper.updateUserLastLoginByUuid(uuid, lastLoginAt, lastLoginSource);
        if (affectedRows != 1) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    // 记录一次登录来源。
    public void insertUserLoginRecord(String uuid, String loginSource) {
        if (uuid == null || uuid.isBlank() || loginSource == null || loginSource.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        int affectedRows = userMapper.insertUserLoginRecord(uuid, loginSource);
        if (affectedRows != 1) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
    }
}
