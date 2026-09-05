package com.onlikee.auth.model.vo;

import com.onlikee.user.model.vo.UserInfoVO;
import lombok.Data;

@Data
public class UserLoginVO {
    private String loginSource;
    private UserInfoVO userInfo;
}
