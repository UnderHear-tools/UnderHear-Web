package com.onlikee.application.converter;

import com.onlikee.application.model.dto.request.ApplicationCreateCollectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateConnectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateNewDort;
import com.onlikee.application.model.entity.ApplicationCollect;
import com.onlikee.application.model.entity.ApplicationConnect;
import com.onlikee.application.model.entity.ApplicationNew;
import com.onlikee.user.model.entity.User;
import com.onlikee.application.util.ApplicationUuidGenerator;
import com.onlikee.common.util.FileSizeFormatter;

public final class ToEntity {

    private ToEntity() {
    }

    public static ApplicationNew toApplicationNew(User user, ApplicationCreateNewDort request) {
        ApplicationNew application = new ApplicationNew();
        String appid = ApplicationUuidGenerator.next();
        String originalFilename = request.getAppFile().getOriginalFilename();

        application.setAppid(appid);
        application.setOwnerUuid(user.getUuid());
        application.setFramework(request.getFramework());
        application.setAppName(request.getAppName());
        application.setAppSubDomain(request.getAppSubDomain());
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        application.setOriginalFilename(originalFilename);
        application.setOriginalFileType(request.getAppFile().getContentType());
        application.setOriginalFileSize(FileSizeFormatter.format(request.getAppFile().getSize()));
        return application;
    }

    public static ApplicationConnect toApplicationConnect(
            User user,
            ApplicationCreateConnectDort request,
            String appUrl) {
        ApplicationConnect application = new ApplicationConnect();
        String appid = ApplicationUuidGenerator.next();

        application.setAppid(appid);
        application.setOwnerUuid(user.getUuid());
        application.setAppName(request.getAppName());
        application.setAppUrl(appUrl);
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        return application;
    }

    public static ApplicationCollect toApplicationCollect(
            User user,
            ApplicationCreateCollectDort request,
            String appUrl) {
        ApplicationCollect application = new ApplicationCollect();
        String appid = ApplicationUuidGenerator.next();

        application.setAppid(appid);
        application.setOwnerUuid(user.getUuid());
        application.setAppName(request.getAppName());
        application.setAppUrl(appUrl);
        application.setVisibility(request.getVisibility());
        application.setAppDescription(request.getAppDescription());
        return application;
    }
}
