package com.onlikee.auth.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.auth.converter.ToDore;
import com.onlikee.auth.oauth.model.dto.request.OAuthSignupCompleteDort;
import com.onlikee.auth.model.dto.response.UserLoginDore;
import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.auth.service.AuthCookieService;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.auth.oauth.service.OAuthSignupService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/oauth/signup")
public class OAuthSignupController {

    @Autowired
    private OAuthSignupService oauthSignupService;

    @Autowired
    private AuthCookieService authCookieService;

    @Autowired
    private SessionAuthService sessionAuthService;

    @PostMapping("/complete")
    public ApiResponse<UserLoginDore> complete(
            @Valid @RequestBody OAuthSignupCompleteDort request,
            @CookieValue(value = "auth_token", required = false) String oldToken,
            HttpServletResponse response) {
        // 完善资料创建正式账号时，清理旧登录态，避免同一浏览器保留过期会话。
        sessionAuthService.logoutIfPresent(oldToken);

        UserLoginWithTokenDore userLoginWithTokenDore = oauthSignupService.complete(request);
        authCookieService.writeToken(response, userLoginWithTokenDore.getToken());
        return ApiResponse.success(ToDore.toUserLoginDore(userLoginWithTokenDore));
    }
}
