package com.onlikee.service.oauth;

import com.onlikee.pojo.dto.request.UserGithubDort;
import com.onlikee.pojo.dto.response.OAuthCallbackWithTokenDore;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGithubService {

    OAuthCallbackWithTokenDore login(AuthResponse<AuthUser> authResponse);

    //在oauth_github表中检测该用户是否存在
    boolean exists(UserGithubDort userGithubDort);
}
