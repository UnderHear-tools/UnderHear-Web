package com.onlikee.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.application.model.dto.request.ApplicationCreateCollectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateConnectDort;
import com.onlikee.application.model.dto.request.ApplicationCreateNewDort;
import com.onlikee.application.model.dto.response.ApplicationCreateCollectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateConnectDore;
import com.onlikee.application.model.dto.response.ApplicationCreateNewDore;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.user.model.entity.User;
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
    public ApiResponse<ApplicationCreateNewDore> applicationCreateNew(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @ModelAttribute ApplicationCreateNewDort applicationCreateNewDort) {
        User user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateNewDore applicationCreateNewDore = applicationCreateService.applicationCreateNew(user, applicationCreateNewDort);
        return ApiResponse.success("应用创建成功！", applicationCreateNewDore);
    }

    @PostMapping("/connect")
    public ApiResponse<ApplicationCreateConnectDore> applicationCreateConnect(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @RequestBody ApplicationCreateConnectDort applicationCreateConnectDort) {
        User user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateConnectDore applicationCreateConnectDore = applicationCreateService.applicationCreateConnect(user, applicationCreateConnectDort);
        return ApiResponse.success("应用创建成功！", applicationCreateConnectDore);
    }

    @PostMapping("/collect")
    public ApiResponse<ApplicationCreateCollectDore> applicationCreateCollect(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @RequestBody ApplicationCreateCollectDort applicationCreateCollectDort) {
        User user = sessionAuthService.getCurrentUser(token);
        ApplicationCreateCollectDore applicationCreateCollectDore = applicationCreateService.applicationCreateCollect(user, applicationCreateCollectDort);
        return ApiResponse.success("应用收录成功！", applicationCreateCollectDore);
    }
}
