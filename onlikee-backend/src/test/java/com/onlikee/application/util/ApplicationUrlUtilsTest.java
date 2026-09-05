package com.onlikee.application.util;

import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationUrlUtilsTest {

    private Object previousAppDomainSuffix;

    @BeforeEach
    // 单元测试显式准备域名并恢复静态状态，避免依赖执行顺序。
    void configureAppDomain() {
        previousAppDomainSuffix = ReflectionTestUtils.getField(ApplicationUrlUtils.class, "appDomainSuffix");
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", ".example.com");
    }

    @AfterEach
    void restoreAppDomain() {
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", previousAppDomainSuffix);
    }

    @Test
    // 已验证的子域名由后端统一构造为发布域名和对外 URL。
    void buildAppAddressShouldReturnCanonicalValues() {
        assertEquals("demo.example.com", ApplicationUrlUtils.buildAppDomain("demo"));
        assertEquals("https://demo.example.com/", ApplicationUrlUtils.buildAppUrl("demo"));
    }

    @Test
    void buildAppAddressShouldUseConfiguredDomainSuffix() {
        ApplicationUrlUtils urlUtils = new ApplicationUrlUtils();
        urlUtils.setAppDomainSuffix(".test.com");

        assertEquals("demo.test.com", ApplicationUrlUtils.buildAppDomain("demo"));
        assertEquals("https://demo.test.com/", ApplicationUrlUtils.buildAppUrl("demo"));
    }
}
