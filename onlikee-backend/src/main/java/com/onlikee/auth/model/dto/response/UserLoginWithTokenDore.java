package com.onlikee.auth.model.dto.response;

import com.onlikee.user.model.dto.response.UserInfoDore;
import lombok.Data;

@Data
public class UserLoginWithTokenDore {
    private String token;
    private String loginSource;
    private UserInfoDore userInfo;
}
