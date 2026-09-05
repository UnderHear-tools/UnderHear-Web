package com.onlikee.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlikee.user.converter.ToDore;
import com.onlikee.user.model.dto.request.UserProfileDort;
import com.onlikee.user.model.dto.request.UserProfileMarkdownDort;
import com.onlikee.user.model.dto.response.UserInfoDore;
import com.onlikee.user.model.dto.response.UserProfileDore;
import com.onlikee.user.model.dto.response.UserProfileMarkdownDore;
import com.onlikee.common.response.ApiResponse;
import com.onlikee.user.model.entity.User;
import com.onlikee.user.model.entity.UserProfileMarkdown;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.user.service.UserProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SessionAuthService sessionAuthService;

    @GetMapping("/{nickname}/profile")
    // 公开资料基础信息按昵称查询用户，不依赖登录态。
    public ApiResponse<UserProfileDore> profile(@PathVariable String nickname) {
        User user = userProfileService.getUserByNickname(nickname);
        return ApiResponse.success(ToDore.toUserProfileDore(user));
    }

    @GetMapping("/{nickname}/markdown")
    // 公开 Markdown 按昵称查询，只返回 README 内容字段，不返回其他公开资料字段。
    public ApiResponse<UserProfileMarkdownDore> markdown(@PathVariable String nickname) {
        User user = userProfileService.getUserByNickname(nickname);
        UserProfileMarkdown userProfileMarkdown = userProfileService.getMarkdownByUuid(user.getUuid());
        return ApiResponse.success(ToDore.toUserProfileMarkdownDore(userProfileMarkdown));
    }

    @PostMapping("/me/markdown")
    // Markdown 资料页只允许当前登录用户覆盖自己的内容。
    public ApiResponse<Void> saveMarkdown(
            @CookieValue(value = "auth_token", required = false) String token,
            @Valid @RequestBody UserProfileMarkdownDort request) {
        User user = sessionAuthService.getCurrentUser(token);
        userProfileService.saveCurrentUserMarkdown(user, request);
        return ApiResponse.success(null);
    }

    @PostMapping("/me/profile")
    // 基础资料只允许当前登录用户更新自己的公开资料字段。
    public ApiResponse<UserInfoDore> saveProfile(
            @CookieValue(value = "auth_token", required = false) String token,
            @RequestBody UserProfileDort request) {
        User user = sessionAuthService.getCurrentUser(token);
        User updatedUser = userProfileService.saveCurrentUserProfile(user, request);
        return ApiResponse.success(ToDore.toUserInfoDore(updatedUser));
    }
}
