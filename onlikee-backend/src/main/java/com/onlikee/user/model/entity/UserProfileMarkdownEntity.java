package com.onlikee.user.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserProfileMarkdownEntity {
    private Long id;
    private String uuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
