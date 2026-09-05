package com.onlikee.module.application.converter;

import com.onlikee.module.application.util.ApplicationUrlUtils;
import com.onlikee.module.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.module.application.model.entity.ApplicationCollectEntity;
import com.onlikee.module.application.model.entity.ApplicationConnectEntity;
import com.onlikee.module.application.model.entity.ApplicationNewEntity;

public final class ToVO {

    private ToVO() {
    }

    public static ApplicationCreateNewVO toApplicationCreateNewVO(ApplicationNewEntity application) {
        ApplicationCreateNewVO applicationCreateNewVO = new ApplicationCreateNewVO();
        applicationCreateNewVO.setAppUrl(ApplicationUrlUtils.buildAppUrl(application.getAppSubDomain()));
        return applicationCreateNewVO;
    }

    public static ApplicationCreateConnectVO toApplicationCreateConnectVO(ApplicationConnectEntity application) {
        ApplicationCreateConnectVO applicationCreateConnectVO = new ApplicationCreateConnectVO();
        applicationCreateConnectVO.setAppUrl(application.getAppUrl());
        return applicationCreateConnectVO;
    }

    public static ApplicationCreateCollectVO toApplicationCreateCollectVO(ApplicationCollectEntity application) {
        ApplicationCreateCollectVO applicationCreateCollectVO = new ApplicationCreateCollectVO();
        applicationCreateCollectVO.setAppUrl(application.getAppUrl());
        return applicationCreateCollectVO;
    }
}
