package com.underhear.pojo.dto.response;

import lombok.Data;

@Data
public class UserLoginWithTokenDore {
    private String token;
    private String loginSource;
    private UserInfoDore userInfo;
}
