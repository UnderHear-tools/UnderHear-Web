package com.underhear.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underhear.converter.ToDore;
import com.underhear.pojo.dto.request.UserProfileMarkdownDort;
import com.underhear.pojo.dto.response.UserProfileDore;
import com.underhear.pojo.dto.response.common.ApiResponse;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserProfileMarkdown;
import com.underhear.security.SessionAuthService;
import com.underhear.service.user.UserProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SessionAuthService sessionAuthService;

    @GetMapping("/{nickname}")
    // 公开资料页按昵称查询用户，不依赖登录态，保证游客可访问 /@:nickname。
    public ApiResponse<UserProfileDore> profile(@PathVariable String nickname) {
        User user = userProfileService.getUserByNickname(nickname);
        UserProfileMarkdown markdown = userProfileService.getMarkdownByUuid(user.getUuid());
        return ApiResponse.success(ToDore.toUserProfileDore(user, markdown));
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
}
