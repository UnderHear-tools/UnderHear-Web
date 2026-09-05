package com.onlikee.auth.oauth.model.dto;

import com.onlikee.auth.model.dto.UserLoginWithTokenDTO;
import com.onlikee.user.model.dto.UserInfoDTO;
import lombok.Data;

@Data
public class OAuthCallbackWithTokenDTO {
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String SIGNUP_REQUIRED = "SIGNUP_REQUIRED";

    private String status;
    private String token;
    private String loginSource;
    private UserInfoDTO userInfo;
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;

    public static OAuthCallbackWithTokenDTO loginSuccess(UserLoginWithTokenDTO loginWithToken) {
        OAuthCallbackWithTokenDTO callback = new OAuthCallbackWithTokenDTO();
        callback.setStatus(LOGIN_SUCCESS);
        callback.setToken(loginWithToken.getToken());
        callback.setLoginSource(loginWithToken.getLoginSource());
        callback.setUserInfo(loginWithToken.getUserInfo());
        return callback;
    }

    public static OAuthCallbackWithTokenDTO signupRequired(OAuthPendingSignupResultDTO pendingSignup) {
        OAuthCallbackWithTokenDTO callback = new OAuthCallbackWithTokenDTO();
        callback.setStatus(SIGNUP_REQUIRED);
        callback.setPendingSignupToken(pendingSignup.getPendingSignupToken());
        callback.setProvider(pendingSignup.getProvider());
        callback.setAvatarUrl(pendingSignup.getAvatarUrl());
        callback.setSuggestedNickname(pendingSignup.getSuggestedNickname());
        callback.setEmail(pendingSignup.getEmail());
        return callback;
    }
}
