package com.onlikee.user.service;

import com.onlikee.user.model.entity.UserEntity;

public interface UserService {

    // 根据 UUID 查询用户信息。
    UserEntity getUserByUuid(String uuid);

    // 根据 GitHub ID 查询用户信息。
    UserEntity getUserByGithubId(Long githubId);

    // 根据 Gitee ID 查询用户信息。
    UserEntity getUserByGiteeId(Long giteeId);

    // 更新用户最后登录信息。
    void updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource);

    // 记录一次登录来源。
    void insertUserLoginRecord(String uuid, String loginSource);
}
