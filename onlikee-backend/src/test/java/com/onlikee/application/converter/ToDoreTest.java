package com.onlikee.application.converter;

import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.onlikee.application.util.ApplicationUrlUtils;
import com.onlikee.application.model.dto.response.ApplicationCreateCollectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateConnectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateNewDore;
import com.onlikee.application.model.entity.ApplicationCollect;
import com.onlikee.application.model.entity.ApplicationConnect;
import com.onlikee.application.model.entity.ApplicationNew;

class ToDoreTest {

    private Object previousAppDomainSuffix;

    @BeforeEach
    // 单元测试显式准备域名并恢复静态状态，避免依赖执行顺序。
    void configureAppDomain() {
        previousAppDomainSuffix = ReflectionTestUtils.getField(ApplicationUrlUtils.class, "appDomainSuffix");
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", ".onlikee.com");
    }

    @AfterEach
    void restoreAppDomain() {
        ReflectionTestUtils.setField(ApplicationUrlUtils.class, "appDomainSuffix", previousAppDomainSuffix);
    }

    @Test
    // 应用创建响应对象只需向外暴露应用 URL。
    void toApplicationCreateNewDoreShouldBuildAppUrl() {
        ApplicationNew application = new ApplicationNew();
        application.setAppSubDomain("demo");

        ApplicationCreateNewDore dore = ToDore.toApplicationCreateNewDore(application);

        assertEquals("https://demo.onlikee.com/", dore.getAppUrl());
    }

    @Test
    // 接入网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateConnectDoreShouldCopyAppUrl() {
        ApplicationConnect application = new ApplicationConnect();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateConnectDore dore = ToDore.toApplicationCreateConnectDore(application);

        assertEquals("https://www.demo.com", dore.getAppUrl());
    }

    @Test
    // 收录网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateCollectDoreShouldCopyAppUrl() {
        ApplicationCollect application = new ApplicationCollect();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateCollectDore dore = ToDore.toApplicationCreateCollectDore(application);

        assertEquals("https://www.demo.com", dore.getAppUrl());
    }
}
