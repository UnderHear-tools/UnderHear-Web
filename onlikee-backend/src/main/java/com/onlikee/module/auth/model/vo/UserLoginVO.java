package com.onlikee.module.auth.model.vo;

import com.onlikee.module.user.model.vo.UserInfoVO;
import lombok.Data;

@Data
public class UserLoginVO {
    private String loginSource;
    private UserInfoVO userInfo;
}
