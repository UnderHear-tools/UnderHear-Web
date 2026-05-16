package com.onlikee.service.lightoss;

import org.springframework.web.multipart.MultipartFile;

import com.onlikee.pojo.dto.request.LightOssPublishedSiteDort;

public interface LightOssPublishService {

    // 确保用户对应的 bucket 已存在；已存在时按成功处理。
    void ensureBucketExists(String bucketName);

    // 直接发布单个 HTML 文件站点。
    LightOssPublishedSiteDort publishHtml(String bucketName, String appEnglishName, MultipartFile appFile);

    // 发布 ZIP 静态站点，返回后续回滚所需的站点信息。
    LightOssPublishedSiteDort publishZipSite(String bucketName, String appEnglishName, MultipartFile appFile);

    // 在发布成功但后续流程失败时，按已发布内容做补偿清理。
    void cleanupPublishedSite(LightOssPublishedSiteDort publishedSite);
}
