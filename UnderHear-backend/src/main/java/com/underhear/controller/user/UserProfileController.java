package com.underhear.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.converter.ToDore;
import com.underhear.pojo.dto.response.UserInfoDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.pojo.entity.User;
import com.underhear.service.user.UserProfileService;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{nickname}")
    // 公开资料页按昵称查询用户，不依赖登录态，保证游客可访问 /@:nickname。
    public ApiResponse<UserInfoDore> profile(@PathVariable String nickname) {
        User user = userProfileService.getUserByNickname(nickname);
        return ApiResponse.success(ToDore.toUserInfoDore(user));
    }
}
