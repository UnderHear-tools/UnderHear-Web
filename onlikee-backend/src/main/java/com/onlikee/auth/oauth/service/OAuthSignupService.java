package com.onlikee.auth.oauth.service;

import com.onlikee.auth.oauth.model.dto.request.OAuthSignupCompleteDort;
import com.onlikee.auth.oauth.model.dto.request.UserGiteeDort;
import com.onlikee.auth.oauth.model.dto.request.UserGithubDort;
import com.onlikee.auth.oauth.model.dto.response.OAuthPendingSignupDore;
import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;

public interface OAuthSignupService {

    OAuthPendingSignupDore createGithubPendingSignup(UserGithubDort userGithubDort);

    OAuthPendingSignupDore createGiteePendingSignup(UserGiteeDort userGiteeDort);

    UserLoginWithTokenDore complete(OAuthSignupCompleteDort request);
}
