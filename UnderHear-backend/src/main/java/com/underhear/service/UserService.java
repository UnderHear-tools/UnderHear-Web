package com.underhear.service;

import com.underhear.entity.User;
import me.zhyd.oauth.model.AuthUser;

public interface UserService {

    User upsertGithubUser(AuthUser authUser);

    User findById(Long id);
}

