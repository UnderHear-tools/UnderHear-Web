package com.underhear.service.oauth;

import com.underhear.pojo.dto.request.UserGiteeDort;
import com.underhear.pojo.dto.response.UserLoginDore;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGiteeService {

    UserLoginDore login(AuthResponse<AuthUser> authResponse);

    //在oauth_gitee表中检测该用户是否存在
    boolean exists(UserGiteeDort userGiteeDort);
}
