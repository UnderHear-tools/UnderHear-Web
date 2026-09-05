package com.onlikee.application.converter;

import com.onlikee.application.util.ApplicationUrlUtils;
import com.onlikee.application.model.dto.response.ApplicationCreateCollectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateConnectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateNewDore;
import com.onlikee.application.model.entity.ApplicationCollect;
import com.onlikee.application.model.entity.ApplicationConnect;
import com.onlikee.application.model.entity.ApplicationNew;

public final class ToDore {

    private ToDore() {
    }

    public static ApplicationCreateNewDore toApplicationCreateNewDore(ApplicationNew application) {
        ApplicationCreateNewDore applicationCreateNewDore = new ApplicationCreateNewDore();
        applicationCreateNewDore.setAppUrl(ApplicationUrlUtils.buildAppUrl(application.getAppSubDomain()));
        return applicationCreateNewDore;
    }

    public static ApplicationCreateConnectDore toApplicationCreateConnectDore(ApplicationConnect application) {
        ApplicationCreateConnectDore applicationCreateConnectDore = new ApplicationCreateConnectDore();
        applicationCreateConnectDore.setAppUrl(application.getAppUrl());
        return applicationCreateConnectDore;
    }

    public static ApplicationCreateCollectDore toApplicationCreateCollectDore(ApplicationCollect application) {
        ApplicationCreateCollectDore applicationCreateCollectDore = new ApplicationCreateCollectDore();
        applicationCreateCollectDore.setAppUrl(application.getAppUrl());
        return applicationCreateCollectDore;
    }
}
