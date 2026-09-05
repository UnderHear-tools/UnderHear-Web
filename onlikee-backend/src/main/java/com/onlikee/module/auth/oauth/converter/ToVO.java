package com.onlikee.module.auth.oauth.converter;

import com.onlikee.module.auth.oauth.model.vo.OAuthCallbackVO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;

public final class ToVO {

    private ToVO() {
    }

    public static OAuthCallbackVO toOAuthCallbackVO(OAuthCallbackWithTokenDTO callbackWithToken) {
        OAuthCallbackVO callback = new OAuthCallbackVO();
        callback.setStatus(callbackWithToken.getStatus());
        callback.setLoginSource(callbackWithToken.getLoginSource());
        // 待注册分支没有已登录用户资料，继续保留 null。
        callback.setUserInfo(callbackWithToken.getUserInfo() == null ? null
                : com.onlikee.module.user.converter.ToVO.toUserInfoVO(callbackWithToken.getUserInfo()));
        callback.setPendingSignupToken(callbackWithToken.getPendingSignupToken());
        callback.setProvider(callbackWithToken.getProvider());
        callback.setAvatarUrl(callbackWithToken.getAvatarUrl());
        callback.setSuggestedNickname(callbackWithToken.getSuggestedNickname());
        callback.setEmail(callbackWithToken.getEmail());
        return callback;
    }
}
