package com.onlikee.service.oauth;

import com.onlikee.pojo.dto.request.OAuthSignupCompleteDort;
import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.request.UserGithubDort;
import com.onlikee.pojo.dto.response.OAuthPendingSignupDore;
import com.onlikee.pojo.dto.response.UserLoginWithTokenDore;

public interface OAuthSignupService {

    OAuthPendingSignupDore createGithubPendingSignup(UserGithubDort userGithubDort);

    OAuthPendingSignupDore createGiteePendingSignup(UserGiteeDort userGiteeDort);

    UserLoginWithTokenDore complete(OAuthSignupCompleteDort request);
}
