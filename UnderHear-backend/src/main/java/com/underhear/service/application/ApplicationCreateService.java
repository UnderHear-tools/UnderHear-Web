package com.underhear.service.application;

import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.entity.User;

public interface ApplicationCreateService {

    ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort request);
}
