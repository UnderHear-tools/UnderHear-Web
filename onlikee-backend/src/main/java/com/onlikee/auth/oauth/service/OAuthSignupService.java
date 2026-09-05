package com.onlikee.auth.oauth.service;

import com.onlikee.auth.oauth.model.dto.OAuthSignupCompleteDTO;
import com.onlikee.auth.oauth.model.dto.UserGiteeDTO;
import com.onlikee.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.auth.model.dto.UserLoginWithTokenDTO;

public interface OAuthSignupService {

    OAuthPendingSignupResultDTO createGithubPendingSignup(UserGithubDTO userGithubDTO);

    OAuthPendingSignupResultDTO createGiteePendingSignup(UserGiteeDTO userGiteeDTO);

    UserLoginWithTokenDTO complete(OAuthSignupCompleteDTO request);
}
