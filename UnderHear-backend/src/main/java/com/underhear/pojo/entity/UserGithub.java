package com.underhear.pojo.entity;

import lombok.Data;

@Data
public class UserGithub {

    private Long id;
    private String uuid;
    private Long githubId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String githubToken;
}
