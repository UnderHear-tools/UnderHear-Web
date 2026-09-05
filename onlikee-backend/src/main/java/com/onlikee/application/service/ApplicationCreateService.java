package com.onlikee.application.service;

import com.onlikee.application.model.dto.request.ApplicationCreateConnectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateCollectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateNewDort;
import com.onlikee.application.model.dto.response.ApplicationCreateCollectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateConnectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateNewDore;
import com.onlikee.user.model.entity.User;

public interface ApplicationCreateService {

    ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort request);

    ApplicationCreateConnectDore applicationCreateConnect(User user, ApplicationCreateConnectDort request);

    ApplicationCreateCollectDore applicationCreateCollect(User user, ApplicationCreateCollectDort request);
}
