package com.onlikee.pojo.dto.response;

import lombok.Data;

@Data
public class UserLoginDore {
    private String loginSource;
    private UserInfoDore userInfo;
}
