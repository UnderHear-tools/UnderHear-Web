package com.onlikee.controller.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.converter.ToDore;
import com.onlikee.pojo.dto.request.OAuthSignupCompleteDort;
import com.onlikee.pojo.dto.response.UserLoginDore;
import com.onlikee.pojo.dto.response.UserLoginWithTokenDore;
import com.onlikee.pojo.dto.response.common.ApiResponse;
import com.onlikee.security.AuthCookieService;
import com.onlikee.security.SessionAuthService;
import com.onlikee.service.oauth.OAuthSignupService;

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
