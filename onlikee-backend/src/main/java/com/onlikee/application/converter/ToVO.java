package com.onlikee.application.converter;

import com.onlikee.application.util.ApplicationUrlUtils;
import com.onlikee.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.application.model.entity.ApplicationCollectEntity;
import com.onlikee.application.model.entity.ApplicationConnectEntity;
import com.onlikee.application.model.entity.ApplicationNewEntity;

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
