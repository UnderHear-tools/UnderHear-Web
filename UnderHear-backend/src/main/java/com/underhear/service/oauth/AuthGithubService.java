package com.underhear.service.oauth;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

public interface AuthGithubService {

    String login(AuthResponse<AuthUser> authResponse);
}
