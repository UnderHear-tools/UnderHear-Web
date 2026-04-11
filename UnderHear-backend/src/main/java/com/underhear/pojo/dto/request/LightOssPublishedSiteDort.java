package com.underhear.pojo.dto.request;

import lombok.Data;

@Data
// 保存 Light OSS 发布结果，供应用创建失败时执行补偿清理。
public class LightOssPublishedSiteDort {
    private Long siteId;
    private String bucketName;
    private String rootPrefix;
    private String objectKey;
}
