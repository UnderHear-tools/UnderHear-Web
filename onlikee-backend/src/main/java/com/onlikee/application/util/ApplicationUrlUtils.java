package com.onlikee.application.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 应用域名依赖项目配置，独立于通用 URL 处理。
@Component
public class ApplicationUrlUtils {
    private static String appDomainSuffix;

    @Value("${app.domain-suffix}")
    void setAppDomainSuffix(String appDomainSuffix) {
        ApplicationUrlUtils.appDomainSuffix = appDomainSuffix;
    }

    public static String buildAppDomain(String appSubDomain) {
        return appSubDomain + appDomainSuffix;
    }

    public static String buildAppUrl(String appSubDomain) {
        return "https://" + buildAppDomain(appSubDomain) + "/";
    }
}
