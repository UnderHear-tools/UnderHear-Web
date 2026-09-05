package com.onlikee.user.service;

import com.onlikee.user.model.dto.request.UserProfileMarkdownDort;
import com.onlikee.user.model.dto.request.UserProfileDort;
import com.onlikee.user.model.entity.User;
import com.onlikee.user.model.entity.UserProfileMarkdown;

public interface UserProfileService {

    // 根据昵称查询公开用户资料。
    User getUserByNickname(String nickname);

    // 根据用户 uuid 查询公开资料页 Markdown 内容。
    UserProfileMarkdown getMarkdownByUuid(String uuid);

    // 保存当前用户的 Markdown 资料页，uuid 只能来自登录态用户。
    void saveCurrentUserMarkdown(User user, UserProfileMarkdownDort request);

    // 保存当前用户的公开基础资料，uuid 只能来自登录态用户。
    User saveCurrentUserProfile(User user, UserProfileDort request);
}
