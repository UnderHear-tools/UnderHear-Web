package com.onlikee.auth.oauth.model.dto;

import lombok.Data;

@Data
public class OAuthPendingSignupResultDTO {
    private String pendingSignupToken;
    private String provider;
    private String avatarUrl;
    private String suggestedNickname;
    private String email;
}
