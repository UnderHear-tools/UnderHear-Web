package com.underhear.service.oauth;

import com.underhear.pojo.dto.request.UserGithubDort;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGithubService {

    String login(AuthResponse<AuthUser> authResponse);

    //在oauth_github表中检测该用户是否存在
    boolean exists(UserGithubDort userGithubDort);
}
