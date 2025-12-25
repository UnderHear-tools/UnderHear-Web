package com.underhear.service.api;

import com.underhear.pojo.dto.request.UserGithubDORT;

public interface UserService {
    //在github_user表中检测该用户是否存在
    boolean exists(UserGithubDORT userGithubDORT);
}
