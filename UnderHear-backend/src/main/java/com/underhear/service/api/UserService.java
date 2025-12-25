package com.underhear.service.api;

import com.underhear.pojo.entity.User;

public interface UserService {

    User findUserByGithubId(Long githubId);
}
