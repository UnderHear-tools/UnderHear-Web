package com.onlikee.module.auth.converter;

import com.onlikee.module.auth.model.dto.UserLoginWithTokenDTO;
import com.onlikee.module.user.model.entity.UserEntity;

// token 只在内部登录结果中流转，由 controller 写入 Cookie。
public final class ToDTO {

    private ToDTO() {
    }

    public static UserLoginWithTokenDTO toUserLoginWithTokenDTO(UserEntity user, String token) {
        UserLoginWithTokenDTO userLoginWithTokenDTO = new UserLoginWithTokenDTO();
        userLoginWithTokenDTO.setToken(token);
        String loginSource = user.getLastLoginSource();
        userLoginWithTokenDTO.setLoginSource(loginSource);
        userLoginWithTokenDTO.setUserInfo(com.onlikee.module.user.converter.ToDTO.toUserInfoDTO(user));
        return userLoginWithTokenDTO;
    }
}
