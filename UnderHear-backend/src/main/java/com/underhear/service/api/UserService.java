package com.underhear.service.api;

import com.underhear.pojo.entity.User;

public interface UserService {

    User getUserByGithubId(Long githubId);

    int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource);

    int insertUserLoginRecord(String uuid, String loginSource);
}
