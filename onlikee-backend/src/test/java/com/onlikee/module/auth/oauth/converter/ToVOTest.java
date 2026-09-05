package com.onlikee.module.auth.oauth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.module.auth.oauth.model.vo.OAuthCallbackVO;
import com.onlikee.module.user.model.dto.UserInfoDTO;

import tools.jackson.databind.json.JsonMapper;

class ToVOTest {

    @Test
    // 内部 token 交给 Cookie 层，前端 JSON 只包含登录状态和展示资料。
    void loginResultShouldExposeUserInfoWithoutToken() {
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setUuid("user-1");
        userInfo.setNickname("tester");
        UserLoginWithTokenDTO login = new UserLoginWithTokenDTO();
        login.setToken("internal-token");
        login.setLoginSource("GITHUB_OAUTH");
        login.setUserInfo(userInfo);

        OAuthCallbackVO response = ToVO.toOAuthCallbackVO(OAuthCallbackWithTokenDTO.loginSuccess(login));

        assertEquals("LOGIN_SUCCESS", response.getStatus());
        assertEquals("GITHUB_OAUTH", response.getLoginSource());
        assertEquals("user-1", response.getUserInfo().getUuid());
        assertEquals("tester", response.getUserInfo().getNickname());
        JsonMapper mapper = JsonMapper.builder().build();
        assertFalse(mapper.readTree(mapper.writeValueAsString(response)).has("token"));
    }

    @Test
    // 待注册结果只传递注册提示信息，userInfo 继续为空。
    void pendingSignupShouldPreserveHintsWithoutLoggedInUser() {
        OAuthPendingSignupResultDTO pending = new OAuthPendingSignupResultDTO();
        pending.setPendingSignupToken("pending-token");
        pending.setProvider("gitee");
        pending.setAvatarUrl("https://avatar.example.com/user.png");
        pending.setSuggestedNickname("tester");
        pending.setEmail("tester@example.com");

        OAuthCallbackVO response = ToVO.toOAuthCallbackVO(OAuthCallbackWithTokenDTO.signupRequired(pending));

        assertEquals("SIGNUP_REQUIRED", response.getStatus());
        assertEquals(pending.getPendingSignupToken(), response.getPendingSignupToken());
        assertEquals(pending.getProvider(), response.getProvider());
        assertEquals(pending.getAvatarUrl(), response.getAvatarUrl());
        assertEquals(pending.getSuggestedNickname(), response.getSuggestedNickname());
        assertEquals(pending.getEmail(), response.getEmail());
        assertNull(response.getUserInfo());
        assertNull(response.getLoginSource());
    }
}
