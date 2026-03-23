package com.underhear.service.user;

import com.underhear.pojo.entity.User;

public interface UserService {

    // 根据 UUID 查询用户信息。
    User getUserByUuid(String uuid);

    // 根据 GitHub ID 查询用户信息。
    User getUserByGithubId(Long githubId);

    // 根据 Gitee ID 查询用户信息。
    User getUserByGiteeId(Long giteeId);

    // 更新用户最后登录信息，返回受影响的行数。
    int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource);

    // 记录一次登录来源，返回受影响的行数。
    int insertUserLoginRecord(String uuid, String loginSource);
}
