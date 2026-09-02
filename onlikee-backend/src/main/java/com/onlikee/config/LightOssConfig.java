package com.onlikee.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.onlikee.lightoss.LightOssClient;

@Configuration
public class LightOssConfig {

    // 共享不可变的 SDK 客户端，并在 Spring 容器关闭时释放它创建的 HTTP 资源。
    @Bean(destroyMethod = "close")
    public LightOssClient lightOssClient(
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

        return LightOssClient.builder(URI.create(normalizedApiBaseUrl))
                .bearerToken(normalizedBearerToken)
                .build();
    }
}
