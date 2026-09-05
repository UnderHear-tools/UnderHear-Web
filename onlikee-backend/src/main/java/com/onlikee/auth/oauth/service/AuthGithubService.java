package com.onlikee.auth.oauth.service;

import com.onlikee.auth.oauth.model.dto.request.UserGithubDort;
import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackWithTokenDore;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGithubService {

    OAuthCallbackWithTokenDore login(AuthResponse<AuthUser> authResponse);

    //在oauth_github表中检测该用户是否存在
    boolean exists(UserGithubDort userGithubDort);
}
