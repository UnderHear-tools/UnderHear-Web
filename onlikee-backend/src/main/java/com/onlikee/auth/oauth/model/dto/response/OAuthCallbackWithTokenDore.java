package com.onlikee.auth.oauth.model.dto.response;

import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;
import com.onlikee.user.model.dto.response.UserInfoDore;
import lombok.Data;

@Data
public class OAuthCallbackWithTokenDore {
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String SIGNUP_REQUIRED = "SIGNUP_REQUIRED";

    private String status;
    private String token;
    private String loginSource;
    private UserInfoDore userInfo;
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;

    public static OAuthCallbackWithTokenDore loginSuccess(UserLoginWithTokenDore loginWithToken) {
        OAuthCallbackWithTokenDore callback = new OAuthCallbackWithTokenDore();
        callback.setStatus(LOGIN_SUCCESS);
        callback.setToken(loginWithToken.getToken());
        callback.setLoginSource(loginWithToken.getLoginSource());
        callback.setUserInfo(loginWithToken.getUserInfo());
        return callback;
    }

    public static OAuthCallbackWithTokenDore signupRequired(OAuthPendingSignupDore pendingSignup) {
        OAuthCallbackWithTokenDore callback = new OAuthCallbackWithTokenDore();
        callback.setStatus(SIGNUP_REQUIRED);
        callback.setPendingSignupToken(pendingSignup.getPendingSignupToken());
        callback.setProvider(pendingSignup.getProvider());
        callback.setAvatarUrl(pendingSignup.getAvatarUrl());
        callback.setSuggestedNickname(pendingSignup.getSuggestedNickname());
        callback.setEmail(pendingSignup.getEmail());
        return callback;
    }
}
