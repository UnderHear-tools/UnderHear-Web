package com.onlikee.auth.oauth.converter;

import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackDore;
import com.onlikee.auth.oauth.model.dto.response.OAuthCallbackWithTokenDore;

public final class ToDore {

    private ToDore() {
    }

    public static OAuthCallbackDore toOAuthCallbackDore(OAuthCallbackWithTokenDore callbackWithToken) {
        OAuthCallbackDore callback = new OAuthCallbackDore();
        callback.setStatus(callbackWithToken.getStatus());
        callback.setLoginSource(callbackWithToken.getLoginSource());
        callback.setUserInfo(callbackWithToken.getUserInfo());
        callback.setPendingSignupToken(callbackWithToken.getPendingSignupToken());
        callback.setProvider(callbackWithToken.getProvider());
        callback.setAvatarUrl(callbackWithToken.getAvatarUrl());
        callback.setSuggestedNickname(callbackWithToken.getSuggestedNickname());
        callback.setEmail(callbackWithToken.getEmail());
        return callback;
    }
}
