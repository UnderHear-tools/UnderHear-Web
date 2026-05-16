package com.onlikee.pojo.entity;

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
    private String originalFilename;
    private String originalFileType;
    private String originalFileSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
