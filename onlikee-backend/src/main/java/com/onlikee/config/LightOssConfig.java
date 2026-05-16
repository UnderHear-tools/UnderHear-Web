package com.onlikee.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Configuration
public class LightOssConfig {

    // 为 Light OSS API 创建独立的 RestClient，避免和站内其他 HTTP 调用混用配置。
    @Bean
    @Qualifier("lightOssRestClient")
    public RestClient lightOssRestClient(
            @Value("${light-oss.api-base-url}") String apiBaseUrl,
            @Value("${light-oss.bearer-token}") String bearerToken) {
        String normalizedApiBaseUrl = apiBaseUrl == null ? "" : apiBaseUrl.trim();
        String normalizedBearerToken = bearerToken == null ? "" : bearerToken.trim();

        // 启动阶段直接校验关键配置，避免发布流程运行时才因配置缺失失败。
        if (!StringUtils.hasText(normalizedApiBaseUrl)) {
            throw new IllegalStateException("light-oss.api-base-url must not be blank");
        }
        if (!StringUtils.hasText(normalizedBearerToken)) {
            throw new IllegalStateException("light-oss.bearer-token must not be blank");
        }

        return RestClient.builder()
                .baseUrl(normalizedApiBaseUrl)
                .defaultHeader("Authorization", "Bearer " + normalizedBearerToken)
                .build();
    }
}
