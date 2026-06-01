package com.onlikee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;

class ApplicationUrlTest {

    @Test
    // 合法应用地址应保留完整 URL，并提取用于 Light OSS 发布的站点前缀。
    void parseShouldAcceptOnlikeeSiteUrl() {
        ApplicationUrl applicationUrl = ApplicationUrl.parse("https://demo-app.onlikee.cn/");

        assertEquals("https://demo-app.onlikee.cn/", applicationUrl.value());
        assertEquals("demo-app", applicationUrl.sitePrefix());
    }

    @Test
    // 单字符前缀是合法的最短应用地址。
    void parseShouldAcceptSingleCharacterPrefix() {
        ApplicationUrl applicationUrl = ApplicationUrl.parse("https://a.onlikee.cn/");

        assertEquals("a", applicationUrl.sitePrefix());
    }

    @Test
    // 任何非规范 URL 都不能进入发布和落库流程。
    void parseShouldRejectNonCanonicalUrls() {
        assertInvalid("https://demo.onlikee.cn");
        assertInvalid("http://demo.onlikee.cn/");
        assertInvalid("https://Demo.onlikee.cn/");
        assertInvalid("https://demo.onlikee.cn/path");
        assertInvalid("https://a.b.onlikee.cn/");
        assertInvalid("https://demo.underhear.cn/");
        assertInvalid("https://-demo.onlikee.cn/");
        assertInvalid("https://demo-.onlikee.cn/");
        assertInvalid("https://aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.onlikee.cn/");
    }

    private void assertInvalid(String value) {
        BizException exception = assertThrows(BizException.class, () -> ApplicationUrl.parse(value));
        assertEquals(ErrorCode.APP_URL_INVALID.getCode(), exception.getCode());
    }
}
