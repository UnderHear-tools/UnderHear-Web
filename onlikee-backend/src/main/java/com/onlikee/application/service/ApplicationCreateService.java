package com.onlikee.application.service;

import com.onlikee.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.user.model.entity.UserEntity;

public interface ApplicationCreateService {

    ApplicationCreateNewVO applicationCreateNew(UserEntity user, ApplicationCreateNewDTO request);

    ApplicationCreateConnectVO applicationCreateConnect(UserEntity user, ApplicationCreateConnectDTO request);

    ApplicationCreateCollectVO applicationCreateCollect(UserEntity user, ApplicationCreateCollectDTO request);
}
