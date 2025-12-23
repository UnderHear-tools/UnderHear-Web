package com.underhear.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

    private Long id;
    private Long githubId;
    private String login;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String githubToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
