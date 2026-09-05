package com.onlikee.auth.oauth.model.dto.response;

import com.onlikee.user.model.dto.response.UserInfoDore;
import lombok.Data;

@Data
public class OAuthCallbackDore {
    private String status;
    private String loginSource;
    private UserInfoDore userInfo;
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;
}
