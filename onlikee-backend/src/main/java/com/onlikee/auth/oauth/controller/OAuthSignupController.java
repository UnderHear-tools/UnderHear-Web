package com.onlikee.auth.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.auth.converter.ToVO;
import com.onlikee.auth.oauth.model.dto.OAuthSignupCompleteDTO;
import com.onlikee.auth.model.vo.UserLoginVO;
import com.onlikee.auth.model.dto.UserLoginWithTokenDTO;
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
    public ApiResponse<UserLoginVO> complete(
            @Valid @RequestBody OAuthSignupCompleteDTO request,
            @CookieValue(value = "auth_token", required = false) String oldToken,
            HttpServletResponse response) {
        // 完善资料创建正式账号时，清理旧登录态，避免同一浏览器保留过期会话。
        sessionAuthService.logoutIfPresent(oldToken);

        UserLoginWithTokenDTO userLoginWithTokenDTO = oauthSignupService.complete(request);
        authCookieService.writeToken(response, userLoginWithTokenDTO.getToken());
        return ApiResponse.success(ToVO.toUserLoginVO(userLoginWithTokenDTO));
    }
}
