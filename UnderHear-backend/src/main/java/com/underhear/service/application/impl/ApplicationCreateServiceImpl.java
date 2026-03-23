package com.underhear.service.application.impl;

import org.springframework.stereotype.Service;

import com.underhear.converter.ToEntity;
import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;
import com.underhear.service.application.ApplicationCreateService;

@Service
public class ApplicationCreateServiceImpl implements ApplicationCreateService {

    @Override
    public void applicationCreateNew(User user, ApplicationCreateNewDort request) {
        Application application = ToEntity.toApplication(user, request);
    }
}
