package com.underhear.controller.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.converter.ToDore;
import com.underhear.pojo.dto.request.OAuthSignupCompleteDort;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.security.AuthCookieService;
import com.underhear.security.SessionAuthService;
import com.underhear.service.oauth.OAuthSignupService;

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
