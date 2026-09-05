package com.onlikee.module.auth.oauth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OAuthSignupCompleteDTO {
    @NotBlank(message = "pendingSignupToken不能为空")
    private String pendingSignupToken;

    @NotBlank(message = "nickname不能为空")
    private String nickname;

    @NotBlank(message = "email不能为空")
    private String email;
}
