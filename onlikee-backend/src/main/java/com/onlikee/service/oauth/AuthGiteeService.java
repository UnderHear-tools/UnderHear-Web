package com.onlikee.service.oauth;

import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.response.OAuthCallbackWithTokenDore;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGiteeService {

    OAuthCallbackWithTokenDore login(AuthResponse<AuthUser> authResponse);

    //在oauth_gitee表中检测该用户是否存在
    boolean exists(UserGiteeDort userGiteeDort);
}
