package com.underhear.service.api;

import com.underhear.pojo.entity.User;

public interface UserService {

    // 根据 GitHub ID 查用户，查不到就返回空
    User getUserByGithubId(Long githubId);

    // 根据 Gitee ID 查用户，查不到就返回空
    User getUserByGiteeId(Long giteeId);

    // 更新最后登录信息，返回受影响行数
    int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource);

    // 记录一次登录来源，返回受影响行数
    int insertUserLoginRecord(String uuid, String loginSource);
}
