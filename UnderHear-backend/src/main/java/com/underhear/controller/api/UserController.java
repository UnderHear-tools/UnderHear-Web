package com.underhear.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.converter.ToDore;
import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.pojo.entity.User;
import com.underhear.security.AuthCookieService;
import com.underhear.security.SessionAuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private SessionAuthService sessionAuthService;
    @Autowired
    private AuthCookieService authCookieService;

    @GetMapping("/me")
    public ApiResponse<UserInfoDore> me(@CookieValue(value = "auth_token", required = false) String token) {
        User user = sessionAuthService.getCurrentUser(token);
        return ApiResponse.success(ToDore.toUserInfoDore(user));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @CookieValue(value = "auth_token", required = false) String token,
            HttpServletResponse response) {
        sessionAuthService.logout(token);
        authCookieService.clearToken(response);
        return ApiResponse.success(null);
    }

    @PostMapping("/logout-all")
    public ApiResponse<Void> logoutAll(
            @CookieValue(value = "auth_token", required = false) String token,
            HttpServletResponse response) {
        sessionAuthService.logoutAll(token);
        authCookieService.clearToken(response);
        return ApiResponse.success(null);
    }
}
