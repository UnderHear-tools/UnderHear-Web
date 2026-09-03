package com.onlikee.pojo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApplicationConnect {
    private Long id;
    private String appid;
    private String ownerUuid;
    private String appName;
    private String appUrl;
    private String visibility;
    private String appDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
