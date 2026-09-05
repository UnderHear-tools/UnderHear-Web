package com.onlikee.auth.oauth.service;

import com.onlikee.auth.oauth.model.dto.request.UserGiteeDort;
import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackWithTokenDore;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGiteeService {

    OAuthCallbackWithTokenDore login(AuthResponse<AuthUser> authResponse);

    //在oauth_gitee表中检测该用户是否存在
    boolean exists(UserGiteeDort userGiteeDort);
}
