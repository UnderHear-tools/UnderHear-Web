package com.onlikee.auth.oauth.model.dto.response;

import lombok.Data;

@Data
public class OAuthPendingSignupDore {
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;
}
