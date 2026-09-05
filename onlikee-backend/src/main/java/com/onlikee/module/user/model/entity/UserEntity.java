package com.onlikee.module.user.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String uuid;
    private String nickName;
    private String email;
    private String avatarUrl;
    private String bio;
    private String pronoun;
    private String location;
    private String socialAccount0;
    private String socialAccount1;
    private String socialAccount2;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private String lastLoginSource;
}

