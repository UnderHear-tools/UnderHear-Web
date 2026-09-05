package com.onlikee.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.application.model.dto.ApplicationCreateCollectDTO;
import com.onlikee.application.model.dto.ApplicationCreateConnectDTO;
import com.onlikee.application.model.dto.ApplicationCreateNewDTO;
import com.onlikee.application.model.vo.ApplicationCreateCollectVO;
import com.onlikee.application.model.vo.ApplicationCreateConnectVO;
import com.onlikee.application.model.vo.ApplicationCreateNewVO;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.application.service.ApplicationCreateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/application/create")
public class ApplicationCreateController {
    @Autowired
    private ApplicationCreateService applicationCreateService;

    @Autowired
    private SessionAuthService sessionAuthService;

    //consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    //非 multipart/form-data 的请求会在映射阶段就被挡住，通常返回 415 Unsupported Media Type
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApplicationCreateNewVO> applicationCreateNew(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @ModelAttribute ApplicationCreateNewDTO applicationCreateNewDTO) {
        UserEntity user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateNewVO applicationCreateNewVO = applicationCreateService.applicationCreateNew(user, applicationCreateNewDTO);
        return ApiResponse.success("应用创建成功！", applicationCreateNewVO);
    }

    @PostMapping("/connect")
    public ApiResponse<ApplicationCreateConnectVO> applicationCreateConnect(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @RequestBody ApplicationCreateConnectDTO applicationCreateConnectDTO) {
        UserEntity user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateConnectVO applicationCreateConnectVO = applicationCreateService.applicationCreateConnect(user, applicationCreateConnectDTO);
        return ApiResponse.success("应用创建成功！", applicationCreateConnectVO);
    }

    @PostMapping("/collect")
    public ApiResponse<ApplicationCreateCollectVO> applicationCreateCollect(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @RequestBody ApplicationCreateCollectDTO applicationCreateCollectDTO) {
        UserEntity user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateCollectVO applicationCreateCollectVO = applicationCreateService.applicationCreateCollect(user, applicationCreateCollectDTO);
        return ApiResponse.success("应用收录成功！", applicationCreateCollectVO);
    }
}
