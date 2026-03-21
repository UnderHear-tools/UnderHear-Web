package com.underhear.controller.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.pojo.dto.response.common.ApiResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/application/create")
public class ApplicationCreateController {
    
    @PostMapping("/new")
    public ApiResponse<String> applicationCreateNew(@CookieValue(value = "auth_token", required = false) String token) {
        
    }
}
