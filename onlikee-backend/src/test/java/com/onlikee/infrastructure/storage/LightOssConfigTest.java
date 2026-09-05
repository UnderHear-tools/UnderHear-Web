package com.onlikee.infrastructure.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.onlikee.lightoss.LightOssClient;
import com.onlikee.lightoss.SiteClient;
import com.onlikee.lightoss.exception.LightOssConfigurationException;
import com.onlikee.lightoss.transfer.UploadSource;
import com.sun.net.httpserver.HttpServer;

class LightOssConfigTest {

    @Test
    // API 地址为空时应在创建 SDK 客户端前直接失败。
    void lightOssClientShouldRejectBlankApiBaseUrl() {
        LightOssConfig config = new LightOssConfig();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.lightOssClient(" ", "token"));

        assertEquals("light-oss.api-base-url must not be blank", exception.getMessage());
    }

    @Test
    // Bearer Token 为空时应在创建 SDK 客户端前直接失败。
    void lightOssClientShouldRejectBlankBearerToken() {
        LightOssConfig config = new LightOssConfig();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.lightOssClient("http://localhost:8081", " "));

        assertEquals("light-oss.bearer-token must not be blank", exception.getMessage());
    }

    @Test
    // 配置完整时应成功创建并关闭 SDK 客户端。
    void lightOssClientShouldReturnClosableClientWhenConfigIsValid() {
        LightOssConfig config = new LightOssConfig();

        LightOssClient client = config.lightOssClient("http://localhost:8081", "light-oss");

        assertNotNull(client);
        client.close();
        assertThrows(LightOssConfigurationException.class, () -> client.health().liveness());
    }

    @Test
    // 用真实 SDK 和本地 HTTP 端点验证 multipart、认证头与 Boot 管理的 Jackson 运行时兼容。
    void lightOssClientShouldPublishFileAgainstLocalHttpEndpoint() throws Exception {
        AtomicReference<String> authorization = new AtomicReference<>();
        AtomicReference<String> requestBody = new AtomicReference<>();
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/api/v1/sites/publish/file", exchange -> {
            authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
            requestBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));

            String requestId = exchange.getRequestHeaders().getFirst("X-Request-ID");
            byte[] body = ("{\"request_id\":\"" + requestId + "\",\"data\":{"
                    + "\"id\":1,\"bucket\":\"user-1\",\"root_prefix\":\"demo/\","
                    + "\"enabled\":true,\"index_document\":\"index.html\",\"error_document\":\"\","
                    + "\"spa_fallback\":true,\"domains\":[\"demo.onlikee.com\"],"
                    + "\"created_at\":\"2026-01-01T00:00:00Z\","
                    + "\"updated_at\":\"2026-01-01T00:00:00Z\"}}")
                    .getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(201, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.start();

        try (LightOssClient client = new LightOssConfig().lightOssClient(
                "http://127.0.0.1:" + server.getAddress().getPort(),
                "light-oss")) {
            UploadSource source = UploadSource.fromBytes(
                    "index.html",
                    "text/html",
                    "<html></html>".getBytes(StandardCharsets.UTF_8));
            SiteClient.PublishFileRequest request = SiteClient.PublishFileRequest.builder(
                            "user-1",
                            List.of("demo.onlikee.com"),
                            source)
                    .parentPrefix("demo")
                    .build();

            SiteClient.Site site = client.sites().publishFile(request).data();

            assertEquals(1L, site.id());
            assertEquals("demo/", site.rootPrefix());
            assertEquals("Bearer light-oss", authorization.get());
            assertTrue(requestBody.get().contains("name=\"parent_prefix\""));
            assertTrue(requestBody.get().contains("demo.onlikee.com"));
            assertTrue(requestBody.get().contains("filename=\"index.html\""));
        } finally {
            server.stop(0);
        }
    }
}
