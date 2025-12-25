package com.underhear.pojo.dto.request;

import lombok.Data;

@Data
public class UserGithubDORT {
    private Long githubId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String githubToken;
}
