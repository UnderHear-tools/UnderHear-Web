package com.onlikee.auth.converter;

import com.onlikee.auth.model.vo.UserLoginVO;
import com.onlikee.auth.model.dto.UserLoginWithTokenDTO;

public final class ToVO {

    private ToVO() {
    }

    public static UserLoginVO toUserLoginVO(UserLoginWithTokenDTO userLoginWithTokenDTO) {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setLoginSource(userLoginWithTokenDTO.getLoginSource());
        userLoginVO.setUserInfo(com.onlikee.user.converter.ToVO.toUserInfoVO(userLoginWithTokenDTO.getUserInfo()));
        return userLoginVO;
    }
}
