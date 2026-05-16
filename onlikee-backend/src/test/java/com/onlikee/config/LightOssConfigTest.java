package com.onlikee.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class LightOssConfigTest {

    @Test
    // API 地址为空时应在创建 RestClient 前直接失败。
    void lightOssRestClientShouldRejectBlankApiBaseUrl() {
        LightOssConfig config = new LightOssConfig();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.lightOssRestClient(" ", "token"));

        assertEquals("light-oss.api-base-url must not be blank", exception.getMessage());
    }

    @Test
    // Bearer Token 为空时应在创建 RestClient 前直接失败。
    void lightOssRestClientShouldRejectBlankBearerToken() {
        LightOssConfig config = new LightOssConfig();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.lightOssRestClient("http://localhost:8081", " "));

        assertEquals("light-oss.bearer-token must not be blank", exception.getMessage());
    }

    @Test
    // 配置完整时应成功创建 RestClient。
    void lightOssRestClientShouldReturnRestClientWhenConfigIsValid() {
        LightOssConfig config = new LightOssConfig();

        RestClient restClient = config.lightOssRestClient("http://localhost:8081", "light-oss");

        assertNotNull(restClient);
    }
}
