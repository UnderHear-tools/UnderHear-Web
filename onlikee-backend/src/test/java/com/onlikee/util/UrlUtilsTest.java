package com.onlikee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlUtilsTest {

    @BeforeEach
    void setUp() {
        new UrlUtils().setAppDomainSuffix(".example.com");
    }

    @Test
    // connect 接口接受完整 HTTP(S) URL。
    void isUrlShouldAcceptHttpAndHttpsUrls() {
        assertTrue(UrlUtils.isUrl("http://www.demo.com"));
        assertTrue(UrlUtils.isUrl("https://www.demo.com/path?from=connect#top"));
        assertTrue(UrlUtils.isUrl("HTTPS://www.demo.com"));
    }

    @Test
    // 裸域名由服务层补全协议后再落库。
    void isUrlShouldAcceptBareDomains() {
        assertTrue(UrlUtils.isUrl("www.demo.com"));
        assertTrue(UrlUtils.isUrl("www.demo.com/path?from=connect#top"));
    }

    @Test
    // 只允许站点访问语义明确的 HTTP(S) 协议。
    void isUrlShouldRejectNonHttpScheme() {
        assertFalse(UrlUtils.isUrl("ftp://www.demo.com"));
        assertFalse(UrlUtils.isUrl("mailto:test@example.com"));
    }

    @Test
    // 缺少 host 的 URI 不能作为应用访问地址。
    void isUrlShouldRejectUrlWithoutHost() {
        assertFalse(UrlUtils.isUrl("https:///path"));
        assertFalse(UrlUtils.isUrl("https://"));
    }

    @Test
    // 空值由 DTO 必填校验兜底，工具方法自身保持 false。
    void isUrlShouldRejectBlankUrl() {
        assertFalse(UrlUtils.isUrl(null));
        assertFalse(UrlUtils.isUrl(""));
        assertFalse(UrlUtils.isUrl("   "));
    }

    @Test
    // URL 内部空格会导致解析失败，避免把明显非法输入落库。
    void isUrlShouldRejectUrlWithWhitespace() {
        assertFalse(UrlUtils.isUrl("https://www.demo .com"));
        assertFalse(UrlUtils.isUrl("https://www.demo.com/a b"));
    }

    @Test
    // 已验证的子域名由后端统一构造为发布域名和对外 URL。
    void buildAppAddressShouldReturnCanonicalValues() {
        assertEquals("demo.example.com", UrlUtils.buildAppDomain("demo"));
        assertEquals("https://demo.example.com/", UrlUtils.buildAppUrl("demo"));
    }

    @Test
    void buildAppAddressShouldUseConfiguredDomainSuffix() {
        UrlUtils urlUtils = new UrlUtils();
        urlUtils.setAppDomainSuffix(".test.com");

        assertEquals("demo.test.com", UrlUtils.buildAppDomain("demo"));
        assertEquals("https://demo.test.com/", UrlUtils.buildAppUrl("demo"));
    }
}
