package com.onlikee.application.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import com.onlikee.application.model.dto.request.ApplicationCreateCollectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateConnectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateNewDort;
import com.onlikee.application.model.entity.ApplicationCollect;
import com.onlikee.application.model.entity.ApplicationConnect;
import com.onlikee.application.model.entity.ApplicationNew;
import com.onlikee.user.model.entity.User;

class ToEntityTest {

    @Test
    // 自建应用转换时应生成 appid、子域名和原始文件元信息。
    void toApplicationNewShouldBuildApplicationMetadata() {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateNewDort request = new ApplicationCreateNewDort();
        request.setFramework("html");
        request.setAppName("Demo");
        request.setAppSubDomain("demo");
        request.setVisibility("public");
        request.setAppDescription("description");
        request.setAppFile(new MockMultipartFile(
                "appFile",
                "index.html",
                "text/html",
                "<html></html>".getBytes()));

        ApplicationNew application = ToEntity.toApplicationNew(user, request);

        assertNotNull(application.getAppid());
        UUID.fromString(application.getAppid());
        assertEquals("user-1", application.getOwnerUuid());
        assertEquals("html", application.getFramework());
        assertEquals("Demo", application.getAppName());
        assertEquals("demo", application.getAppSubDomain());
        assertEquals("public", application.getVisibility());
        assertEquals("description", application.getAppDescription());
        assertEquals("index.html", application.getOriginalFilename());
        assertEquals("text/html", application.getOriginalFileType());
        assertEquals("13 B", application.getOriginalFileSize());
    }

    @Test
    // 已有网站接入转换时不应依赖上传文件元信息。
    void toApplicationConnectShouldBuildApplicationMetadata() {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateConnectDort request = new ApplicationCreateConnectDort();
        request.setAppName("Demo Website");
        request.setAppUrl("https://www.demo.com");
        request.setVisibility("public");
        request.setAppDescription("description");

        ApplicationConnect application = ToEntity.toApplicationConnect(user, request, "https://www.demo.com");

        assertNotNull(application.getAppid());
        UUID.fromString(application.getAppid());
        assertEquals("user-1", application.getOwnerUuid());
        assertEquals("Demo Website", application.getAppName());
        assertEquals("https://www.demo.com", application.getAppUrl());
        assertEquals("public", application.getVisibility());
        assertEquals("description", application.getAppDescription());
    }

    @Test
    // 网站收录转换时应写入 collect 来源，不依赖上传文件元信息。
    void toApplicationCollectShouldBuildApplicationMetadata() {
        User user = new User();
        user.setUuid("user-1");
        ApplicationCreateCollectDort request = new ApplicationCreateCollectDort();
        request.setAppName("Demo Website");
        request.setAppUrl("https://www.demo.com");
        request.setVisibility("public");
        request.setAppDescription("description");

        ApplicationCollect application = ToEntity.toApplicationCollect(user, request, "https://www.demo.com");

        assertNotNull(application.getAppid());
        UUID.fromString(application.getAppid());
        assertEquals("user-1", application.getOwnerUuid());
        assertEquals("Demo Website", application.getAppName());
        assertEquals("https://www.demo.com", application.getAppUrl());
        assertEquals("public", application.getVisibility());
        assertEquals("description", application.getAppDescription());
    }
}
