package com.onlikee.user.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserProfileMarkdown {
    private Long id;
    private String uuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
