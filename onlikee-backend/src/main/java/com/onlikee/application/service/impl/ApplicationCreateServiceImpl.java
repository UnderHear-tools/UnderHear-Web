package com.onlikee.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.onlikee.application.converter.ToDore;
import com.onlikee.application.converter.ToEntity;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.application.mapper.ApplicationCreateMapper;
import com.onlikee.application.model.dto.request.ApplicationCreateCollectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateConnectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateNewDort;
import com.onlikee.application.model.dto.response.ApplicationCreateCollectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateConnectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateNewDore;
import com.onlikee.application.model.entity.ApplicationCollect;
import com.onlikee.application.model.entity.ApplicationConnect;
import com.onlikee.application.model.entity.ApplicationNew;
import com.onlikee.user.model.entity.User;
import com.onlikee.application.service.ApplicationCreateService;
import com.onlikee.application.service.ApplicationSitePublishService;
import com.onlikee.application.service.ApplicationSitePublishService.PublishedSite;
import com.onlikee.common.util.UrlUtils;

@Service
public class ApplicationCreateServiceImpl implements ApplicationCreateService {

    @Autowired
    private ApplicationCreateMapper applicationCreateMapper;

    @Autowired
    private ApplicationSitePublishService applicationSitePublishService;

    @Override
    public ApplicationCreateNewDore applicationCreateNew(User user, ApplicationCreateNewDort applicationCreateNewDort) {
        String appSubDomain = applicationCreateNewDort.getAppSubDomain();
        ApplicationNew application = ToEntity.toApplicationNew(user, applicationCreateNewDort);
        if (applicationCreateMapper.countNewByAppSubDomain(appSubDomain) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        // 先发布站点并拿到回滚信息；后续任一步落库失败都要补偿清理已发布内容。
        PublishedSite publishedSite = applicationSitePublishService.publish(
                user.getUuid(),
                appSubDomain,
                applicationCreateNewDort.getAppFile());

        int rows;
        try {
            rows = applicationCreateMapper.insertApplicationNew(application);
        } catch (DuplicateKeyException ex) {
            applicationSitePublishService.cleanupPublishedSite(publishedSite);
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        } catch (RuntimeException ex) {
            applicationSitePublishService.cleanupPublishedSite(publishedSite);
            throw ex;
        }

        if (rows != 1) {
            applicationSitePublishService.cleanupPublishedSite(publishedSite);
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateNewDore(application);
    }

    @Override
    public ApplicationCreateConnectDore applicationCreateConnect(User user, ApplicationCreateConnectDort applicationCreateConnectDort) {
        String appUrl = UrlUtils.normalizeUrl(UrlUtils.smartCompleteUrl(applicationCreateConnectDort.getAppUrl()));
        ApplicationConnect application = ToEntity.toApplicationConnect(user, applicationCreateConnectDort, appUrl);
        if (applicationCreateMapper.countConnectByAppUrl(appUrl) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        int rows;
        try {
            rows = applicationCreateMapper.insertApplicationConnect(application);
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
        ApplicationCollect application = ToEntity.toApplicationCollect(user, applicationCreateCollectDort, appUrl);
        if (applicationCreateMapper.countCollectByAppUrl(appUrl) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        int rows;
        try {
            rows = applicationCreateMapper.insertApplicationCollect(application);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        if (rows != 1) {
            throw new BizException(ErrorCode.APPLICATION_CREATE_FAILED);
        }
        return ToDore.toApplicationCreateCollectDore(application);
    }

}
