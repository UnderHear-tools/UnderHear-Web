package com.underhear.pojo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Application {
    private Long id;
    private String appid;
    private String ownerUuid;
    private String creationMethod;
    private String framework;
    private String appName;
    private String appEnglishName;
    private String appUrl;
    private String visibility;
    private String appDescription;
    private String storagePath;
    private String originalFilename;
    private String fileType;
    private Double fileSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
