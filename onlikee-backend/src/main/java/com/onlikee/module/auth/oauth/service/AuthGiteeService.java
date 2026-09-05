package com.onlikee.module.auth.oauth.service;

import com.onlikee.module.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGiteeService {

    OAuthCallbackWithTokenDTO login(AuthResponse<AuthUser> authResponse);

    //在oauth_gitee表中检测该用户是否存在
    boolean exists(UserGiteeDTO userGiteeDTO);
}
