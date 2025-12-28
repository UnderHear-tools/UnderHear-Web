package com.underhear.pojo.entity;

import lombok.Data;

@Data
public class UserGitee {

    private Long id;
    private String uuid;
    private Long giteeId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String giteeToken;
}
