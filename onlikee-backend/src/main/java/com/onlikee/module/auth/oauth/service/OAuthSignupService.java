package com.onlikee.module.auth.oauth.service;

import com.onlikee.module.auth.oauth.model.dto.OAuthSignupCompleteDTO;
import com.onlikee.module.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.module.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;

public interface OAuthSignupService {

    OAuthPendingSignupResultDTO createGithubPendingSignup(UserGithubDTO userGithubDTO);

    OAuthPendingSignupResultDTO createGiteePendingSignup(UserGiteeDTO userGiteeDTO);

    UserLoginWithTokenDTO complete(OAuthSignupCompleteDTO request);
}
