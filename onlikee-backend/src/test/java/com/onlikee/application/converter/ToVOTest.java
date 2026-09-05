package com.onlikee.application.converter;

import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.onlikee.application.util.ApplicationUrlUtils;
import com.onlikee.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.application.model.entity.ApplicationCollectEntity;
import com.onlikee.application.model.entity.ApplicationConnectEntity;
import com.onlikee.application.model.entity.ApplicationNewEntity;

class ToVOTest {

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
    void toApplicationCreateNewVOShouldBuildAppUrl() {
        ApplicationNewEntity application = new ApplicationNewEntity();
        application.setAppSubDomain("demo");

        ApplicationCreateNewVO vo = ToVO.toApplicationCreateNewVO(application);

        assertEquals("https://demo.onlikee.com/", vo.getAppUrl());
    }

    @Test
    // 接入网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateConnectVOShouldCopyAppUrl() {
        ApplicationConnectEntity application = new ApplicationConnectEntity();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateConnectVO vo = ToVO.toApplicationCreateConnectVO(application);

        assertEquals("https://www.demo.com", vo.getAppUrl());
    }

    @Test
    // 收录网站响应对象只需向外暴露应用 URL。
    void toApplicationCreateCollectVOShouldCopyAppUrl() {
        ApplicationCollectEntity application = new ApplicationCollectEntity();
        application.setAppUrl("https://www.demo.com");

        ApplicationCreateCollectVO vo = ToVO.toApplicationCreateCollectVO(application);

        assertEquals("https://www.demo.com", vo.getAppUrl());
    }
}
