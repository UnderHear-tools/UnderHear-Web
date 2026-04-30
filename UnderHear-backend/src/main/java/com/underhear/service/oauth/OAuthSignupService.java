package com.underhear.service.oauth;

import com.underhear.pojo.dto.request.OAuthSignupCompleteDort;
import com.underhear.pojo.dto.request.UserGiteeDort;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.dto.response.OAuthPendingSignupDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;

public interface OAuthSignupService {

    OAuthPendingSignupDore createGithubPendingSignup(UserGithubDort userGithubDort);

    OAuthPendingSignupDore createGiteePendingSignup(UserGiteeDort userGiteeDort);

    UserLoginWithTokenDore complete(OAuthSignupCompleteDort request);
}
