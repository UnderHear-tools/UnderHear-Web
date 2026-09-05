package com.onlikee.auth.oauth.model.vo;

import com.onlikee.user.model.vo.UserInfoVO;
import lombok.Data;

@Data
public class OAuthCallbackVO {
    private String status;
    private String loginSource;
    private UserInfoVO userInfo;
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;
}
