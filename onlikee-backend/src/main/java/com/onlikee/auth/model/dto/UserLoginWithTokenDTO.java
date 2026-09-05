package com.onlikee.auth.model.dto;

import com.onlikee.user.model.dto.UserInfoDTO;
import lombok.Data;

@Data
public class UserLoginWithTokenDTO {
    private String token;
    private String loginSource;
    private UserInfoDTO userInfo;
}
