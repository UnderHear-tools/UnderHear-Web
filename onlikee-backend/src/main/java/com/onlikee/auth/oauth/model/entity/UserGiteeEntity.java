package com.onlikee.auth.oauth.model.entity;

import lombok.Data;

@Data
public class UserGiteeEntity {

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
