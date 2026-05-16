package com.onlikee.pojo.dto.request;

import lombok.Data;

@Data
public class UserGithubDort {
    private Long githubId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String githubToken;
}
