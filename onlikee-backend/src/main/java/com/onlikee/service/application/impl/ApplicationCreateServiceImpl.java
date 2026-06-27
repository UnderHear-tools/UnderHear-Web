package com.onlikee.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.onlikee.converter.ToDore;
import com.onlikee.converter.ToEntity;
import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.mapper.application.ApplicationCreateMapper;
import com.onlikee.pojo.dto.request.ApplicationCreateCollectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateConnectDort;
import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.request.LightOssPublishedSiteDort;
import com.onlikee.pojo.dto.response.ApplicationCreateCollectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateConnectDore;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.entity.Application;
import com.onlikee.pojo.entity.User;
import com.onlikee.service.application.ApplicationCreateService;
import com.onlikee.service.lightoss.LightOssPublishService;
import com.onlikee.util.UrlUtils;

@Service
public class ApplicationCreateServiceImpl implements ApplicationCreateService {

    @Autowired
    private ApplicationCreateMapper applicationCreateMapper;

    @Autowired
    private LightOssPublishService lightOssPublishService;

    @Override
    public ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort applicationCreateNewDort) {
        String appUrl = applicationCreateNewDort.getAppUrl();
        Application application = ToEntity.toApplication(user, applicationCreateNewDort);
        if (applicationCreateMapper.countByAppUrl(appUrl) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        // 先发布站点并拿到回滚信息；后续任一步落库失败都要补偿清理已发布内容。
        lightOssPublishService.ensureBucketExists(user.getUuid());
        LightOssPublishedSiteDort publishedSite = publishApplicationSite(user, appUrl, application, applicationCreateNewDort);

        int rows;
        try {
            rows = applicationCreateMapper.insertApplication(application);
        } catch (DuplicateKeyException ex) {
            lightOssPublishService.cleanupPublishedSite(publishedSite);
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
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

    @Override
    public ApplicationCreateConnectDore applicationCreateConnect(User user, ApplicationCreateConnectDort applicationCreateConnectDort) {
        String appUrl = UrlUtils.normalizeUrl(UrlUtils.smartCompleteUrl(applicationCreateConnectDort.getAppUrl()));
        Application application = ToEntity.toApplication(user, applicationCreateConnectDort, appUrl);
        if (applicationCreateMapper.countByAppUrl(appUrl) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        int rows;
        try {
            rows = applicationCreateMapper.insertApplication(application);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        if (rows != 1) {
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateConnectDore(application);
    }

    @Override
    public ApplicationCreateCollectDore applicationCreateCollect(User user, ApplicationCreateCollectDort applicationCreateCollectDort) {
        String appUrl = UrlUtils.normalizeUrl(UrlUtils.smartCompleteUrl(applicationCreateCollectDort.getAppUrl()));
        Application application = ToEntity.toApplication(user, applicationCreateCollectDort, appUrl);
        if (applicationCreateMapper.countByAppUrl(appUrl) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        int rows;
        try {
            rows = applicationCreateMapper.insertApplication(application);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        if (rows != 1) {
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateCollectDore(application);
    }

    private LightOssPublishedSiteDort publishApplicationSite(
            User user,
            String appUrl,
            Application application,
            ApplicationCreateNewDort applicationCreateNewDort) {
        if ("html".equalsIgnoreCase(application.getFramework())) {
            return lightOssPublishService.publishHtml(
                    user.getUuid(),
                    appUrl,
                    applicationCreateNewDort.getAppFile());
        }

        return lightOssPublishService.publishZipSite(
                user.getUuid(),
                appUrl,
                applicationCreateNewDort.getAppFile());
    }
}
