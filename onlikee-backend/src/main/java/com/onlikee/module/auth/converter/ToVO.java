package com.onlikee.module.auth.converter;

import com.onlikee.module.auth.model.vo.UserLoginVO;
import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;

public final class ToVO {

    private ToVO() {
    }

    public static UserLoginVO toUserLoginVO(UserLoginWithTokenDTO userLoginWithTokenDTO) {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setLoginSource(userLoginWithTokenDTO.getLoginSource());
        userLoginVO.setUserInfo(com.onlikee.module.user.converter.ToVO.toUserInfoVO(userLoginWithTokenDTO.getUserInfo()));
        return userLoginVO;
    }
}
