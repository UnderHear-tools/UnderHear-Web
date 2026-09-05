package com.onlikee.module.auth.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.onlikee.module.user.model.dto.UserInfoDTO;
import com.onlikee.module.auth.model.vo.UserLoginVO;
import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;

class ToVOTest {

    @Test
    // 对外登录响应应移除 token，但保留登录来源和用户信息。
    void toUserLoginVOShouldDropTokenAndKeepUserInfo() {
        UserLoginWithTokenDTO withToken = new UserLoginWithTokenDTO();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUuid("user-1");
        withToken.setLoginSource("GITEE_OAUTH");
        withToken.setUserInfo(userInfoDTO);

        UserLoginVO userLoginVO = ToVO.toUserLoginVO(withToken);

        assertEquals("GITEE_OAUTH", userLoginVO.getLoginSource());
        assertEquals(userInfoDTO.getUuid(), userLoginVO.getUserInfo().getUuid());
    }

}
