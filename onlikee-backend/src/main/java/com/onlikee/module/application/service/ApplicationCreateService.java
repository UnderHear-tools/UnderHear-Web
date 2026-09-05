package com.onlikee.module.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.onlikee.module.application.converter.ToVO;
import com.onlikee.module.application.converter.ToEntity;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.module.application.mapper.ApplicationCreateMapper;
import com.onlikee.module.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.module.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.module.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.module.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.module.application.model.entity.ApplicationCollectEntity;
import com.onlikee.module.application.model.entity.ApplicationConnectEntity;
import com.onlikee.module.application.model.entity.ApplicationNewEntity;
import com.onlikee.module.user.model.entity.UserEntity;
import com.onlikee.module.application.service.ApplicationSitePublishService.PublishedSite;
import com.onlikee.common.util.UrlUtils;

@Service
public class ApplicationCreateService {

    @Autowired
    private ApplicationCreateMapper applicationCreateMapper;

    @Autowired
    private ApplicationSitePublishService applicationSitePublishService;

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
