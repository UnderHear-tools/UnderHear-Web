package com.onlikee.module.auth.oauth.model.dto;

import lombok.Data;

@Data
public class UserGiteeDTO {
    private Long giteeId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String giteeToken;
}
