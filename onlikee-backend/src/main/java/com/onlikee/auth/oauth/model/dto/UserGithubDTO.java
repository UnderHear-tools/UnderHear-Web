package com.onlikee.auth.oauth.model.dto;

import lombok.Data;

@Data
public class UserGithubDTO {
    private Long githubId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String githubToken;
}
