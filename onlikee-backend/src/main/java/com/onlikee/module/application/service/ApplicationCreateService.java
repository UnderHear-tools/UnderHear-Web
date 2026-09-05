package com.onlikee.module.application.service;

import com.onlikee.module.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.module.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.module.user.model.entity.UserEntity;

public interface ApplicationCreateService {

    ApplicationCreateNewVO applicationCreateNew(UserEntity user, ApplicationCreateNewDTO request);

    ApplicationCreateConnectVO applicationCreateConnect(UserEntity user, ApplicationCreateConnectDTO request);

    ApplicationCreateCollectVO applicationCreateCollect(UserEntity user, ApplicationCreateCollectDTO request);
}
