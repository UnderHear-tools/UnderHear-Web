package com.underhear.controller.application;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.pojo.dto.request.ApplicationCreateNewDort;
import com.underhear.pojo.dto.response.common.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/application/create")
public class ApplicationCreateController {
    
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> applicationCreateNew(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @ModelAttribute ApplicationCreateNewDort request) {
        return ApiResponse.success(null);
    }
}
