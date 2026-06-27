package com.onlikee.service.application;

import com.onlikee.pojo.dto.request.ApplicationCreateConnectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateCollectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.response.ApplicationCreateCollectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateConnectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.entity.User;

public interface ApplicationCreateService {

    ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort request);

    ApplicationCreateConnectDore applicationCreateConnect(User user, ApplicationCreateConnectDort request);

    ApplicationCreateCollectDore applicationCreateCollect(User user, ApplicationCreateCollectDort request);
}
