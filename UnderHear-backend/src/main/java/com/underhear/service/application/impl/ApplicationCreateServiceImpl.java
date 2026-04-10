package com.underhear.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.underhear.converter.ToDore;
import com.underhear.converter.ToEntity;
import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.application.ApplicationCreateMapper;
import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;
import com.underhear.service.application.ApplicationCreateService;

@Service
public class ApplicationCreateServiceImpl implements ApplicationCreateService {

    @Autowired
    private ApplicationCreateMapper applicationCreateMapper;

    @Override
    @Transactional
    public ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort applicationCreateNewDort) {
        Application application = ToEntity.toApplication(user, applicationCreateNewDort);
        if (applicationCreateMapper.countByAppEnglishName(application.getAppEnglishName()) > 0) {
            throw new BizException(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS);
        }

        int rows;
        try {
            rows = applicationCreateMapper.insertApplication(application);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS);
        }

        if (rows != 1) {
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateNewDore(application);
    }
}
