package com.onlikee.controller.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.pojo.dto.request.ApplicationCreateNewDort;
import com.onlikee.pojo.dto.response.ApplicationCreateNewDore;
import com.onlikee.pojo.dto.response.common.ApiResponse;
import com.onlikee.pojo.entity.User;
import com.onlikee.security.SessionAuthService;
import com.onlikee.service.application.ApplicationCreateService;

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
}
