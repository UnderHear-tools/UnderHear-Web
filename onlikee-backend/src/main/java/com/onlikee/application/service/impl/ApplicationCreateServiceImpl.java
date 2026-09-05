package com.onlikee.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.onlikee.application.converter.ToVO;
import com.onlikee.application.converter.ToEntity;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.application.mapper.ApplicationCreateMapper;
import com.onlikee.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.application.model.entity.ApplicationCollectEntity;
import com.onlikee.application.model.entity.ApplicationConnectEntity;
import com.onlikee.application.model.entity.ApplicationNewEntity;
import com.onlikee.user.model.entity.UserEntity;
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
    public ApplicationCreateNewVO applicationCreateNew(UserEntity user, ApplicationCreateNewDTO applicationCreateNewDTO) {
        String appSubDomain = applicationCreateNewDTO.getAppSubDomain();
        ApplicationNewEntity application = ToEntity.toApplicationNewEntity(user, applicationCreateNewDTO);
        if (applicationCreateMapper.countNewByAppSubDomain(appSubDomain) > 0) {
            throw new BizException(ErrorCode.APP_URL_ALREADY_EXISTS);
        }

        // 先发布站点并拿到回滚信息；后续任一步落库失败都要补偿清理已发布内容。
        PublishedSite publishedSite = applicationSitePublishService.publish(
                user.getUuid(),
                appSubDomain,
                applicationCreateNewDTO.getAppFile());

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
        return ToVO.toApplicationCreateNewVO(application);
    }

    @Override
    public ApplicationCreateConnectVO applicationCreateConnect(UserEntity user, ApplicationCreateConnectDTO applicationCreateConnectDTO) {
        String appUrl = UrlUtils.normalizeUrl(UrlUtils.smartCompleteUrl(applicationCreateConnectDTO.getAppUrl()));
        ApplicationConnectEntity application = ToEntity.toApplicationConnectEntity(user, applicationCreateConnectDTO, appUrl);
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
        return ToVO.toApplicationCreateConnectVO(application);
    }

    @Override
    public ApplicationCreateCollectVO applicationCreateCollect(UserEntity user, ApplicationCreateCollectDTO applicationCreateCollectDTO) {
        String appUrl = UrlUtils.normalizeUrl(UrlUtils.smartCompleteUrl(applicationCreateCollectDTO.getAppUrl()));
        ApplicationCollectEntity application = ToEntity.toApplicationCollectEntity(user, applicationCreateCollectDTO, appUrl);
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
        return ToVO.toApplicationCreateCollectVO(application);
    }

}
