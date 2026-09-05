package com.onlikee.module.application.service;

import org.springframework.web.multipart.MultipartFile;

public interface ApplicationSitePublishService {

    // 确保用户 bucket 存在，并从 ZIP 应用包发布静态站点。
    PublishedSite publish(String bucketName, String appSubDomain, MultipartFile appFile);

    // 应用落库失败时，按发布结果尽力清理站点及其对象。
    void cleanupPublishedSite(PublishedSite publishedSite);

    // 不向应用创建流程暴露 SDK 模型，只保留补偿清理需要的信息。
    record PublishedSite(long siteId, String bucketName, String rootPrefix, String objectKey) {
    }
}
