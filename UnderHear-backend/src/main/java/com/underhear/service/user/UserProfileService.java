package com.underhear.service.user;

import com.underhear.pojo.entity.User;

public interface UserProfileService {

    // 根据昵称查询公开用户资料。
    User getUserByNickname(String nickname);
}
