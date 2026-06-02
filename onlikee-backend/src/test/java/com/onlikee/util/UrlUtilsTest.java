package com.onlikee.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UrlUtilsTest {

    @Test
    // connect 接口只接受前端提交后的完整 HTTP(S) URL。
    void isValidShouldAcceptHttpAndHttpsUrls() {
        assertTrue(UrlUtils.isValid("http://www.demo.com"));
        assertTrue(UrlUtils.isValid("https://www.demo.com/path?from=connect#top"));
        assertTrue(UrlUtils.isValid("HTTPS://www.demo.com"));
    }

    @Test
    // 裸域名属于前端输入阶段，不能作为后端 API 的最终 URL。
    void isValidShouldRejectUrlWithoutScheme() {
        assertFalse(UrlUtils.isValid("www.demo.com"));
    }

    @Test
    // 只允许站点访问语义明确的 HTTP(S) 协议。
    void isValidShouldRejectNonHttpScheme() {
        assertFalse(UrlUtils.isValid("ftp://www.demo.com"));
        assertFalse(UrlUtils.isValid("mailto:test@example.com"));
    }

    @Test
    // 缺少 host 的 URI 不能作为应用访问地址。
    void isValidShouldRejectUrlWithoutHost() {
        assertFalse(UrlUtils.isValid("https:///path"));
        assertFalse(UrlUtils.isValid("https://"));
    }

    @Test
    // 空值由 DTO 必填校验兜底，工具方法自身保持 false。
    void isValidShouldRejectBlankUrl() {
        assertFalse(UrlUtils.isValid(null));
        assertFalse(UrlUtils.isValid(""));
        assertFalse(UrlUtils.isValid("   "));
    }

    @Test
    // URL 内部空格会导致解析失败，避免把明显非法输入落库。
    void isValidShouldRejectUrlWithWhitespace() {
        assertFalse(UrlUtils.isValid("https://www.demo .com"));
        assertFalse(UrlUtils.isValid("https://www.demo.com/a b"));
    }
}
