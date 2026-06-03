package com.onlikee.service.application;

import com.onlikee.pojo.dto.request.ApplicationCreateConnectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.entity.User;

public interface ApplicationCreateService {

    ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort request);

    ApplicationCreateNewDore applicationCreateConnect(User user, ApplicationCreateConnectDort request);
}
