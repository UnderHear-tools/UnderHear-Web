package com.onlikee.module.auth.oauth.service;

import com.onlikee.module.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGithubService {

    OAuthCallbackWithTokenDTO login(AuthResponse<AuthUser> authResponse);

    //在oauth_github表中检测该用户是否存在
    boolean exists(UserGithubDTO userGithubDTO);
}
