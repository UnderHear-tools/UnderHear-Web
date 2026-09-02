package com.onlikee.service.application;

import org.springframework.web.multipart.MultipartFile;

public interface ApplicationSitePublishService {

    // 确保用户 bucket 存在，并按应用框架发布 HTML 或 ZIP 站点。
    PublishedSite publish(String bucketName, String appUrlPrefix, String framework, MultipartFile appFile);

    // 应用落库失败时，按发布结果尽力清理站点及其对象。
    void cleanupPublishedSite(PublishedSite publishedSite);

    // 不向应用创建流程暴露 SDK 模型，只保留补偿清理需要的信息。
    record PublishedSite(long siteId, String bucketName, String rootPrefix, String objectKey) {
    }
}
