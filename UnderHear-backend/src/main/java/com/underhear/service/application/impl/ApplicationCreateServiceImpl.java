package com.underhear.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.underhear.converter.ToDore;
import com.underhear.converter.ToEntity;
import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.application.ApplicationCreateMapper;
import com.underhear.pojo.dto.request.LightOssPublishedSiteDort;
import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.response.ApplicationCreateNewDore;
import com.underhear.pojo.entity.Application;
import com.underhear.pojo.entity.User;
import com.underhear.service.application.ApplicationCreateService;
import com.underhear.service.lightoss.LightOssPublishService;

@Service
public class ApplicationCreateServiceImpl implements ApplicationCreateService {

    @Autowired
    private ApplicationCreateMapper applicationCreateMapper;

    @Autowired
    private LightOssPublishService lightOssPublishService;

    @Override
    public ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort applicationCreateNewDort) {
        Application application = ToEntity.toApplication(user, applicationCreateNewDort);
        if (applicationCreateMapper.countByAppEnglishName(application.getAppEnglishName()) > 0) {
            throw new BizException(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS);
        }

        // 先发布站点并拿到回滚信息；后续任一步落库失败都要补偿清理已发布内容。
        lightOssPublishService.ensureBucketExists(user.getNickName());
        LightOssPublishedSiteDort publishedSite = publishApplicationSite(user, application, applicationCreateNewDort);

        int rows;
        try {
            rows = applicationCreateMapper.insertApplication(application);
        } catch (DuplicateKeyException ex) {
            lightOssPublishService.cleanupPublishedSite(publishedSite);
            throw new BizException(ErrorCode.APP_ENGLISH_NAME_ALREADY_EXISTS);
        } catch (RuntimeException ex) {
            lightOssPublishService.cleanupPublishedSite(publishedSite);
            throw ex;
        }

        if (rows != 1) {
            lightOssPublishService.cleanupPublishedSite(publishedSite);
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateNewDore(application);
    }

    private LightOssPublishedSiteDort publishApplicationSite(
            User user,
            Application application,
            ApplicationCreateNewDort applicationCreateNewDort) {
        if ("html".equalsIgnoreCase(application.getFramework())) {
            return lightOssPublishService.publishHtml(
                    user.getNickName(),
                    application.getAppEnglishName(),
                    applicationCreateNewDort.getAppFile());
        }

        return lightOssPublishService.publishZipSite(
                user.getNickName(),
                application.getAppEnglishName(),
                applicationCreateNewDort.getAppFile());
    }
}
