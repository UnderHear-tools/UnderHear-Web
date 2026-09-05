package com.onlikee.module.auth.model.dto;

import com.onlikee.module.user.model.dto.UserInfoDTO;
import lombok.Data;

@Data
public class UserLoginWithTokenDTO {
    private String token;
    private String loginSource;
    private UserInfoDTO userInfo;
}
