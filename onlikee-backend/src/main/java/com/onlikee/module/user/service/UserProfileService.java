package com.onlikee.module.user.service;

import com.onlikee.module.user.model.dto.UserProfileMarkdownDTO;
import com.onlikee.module.user.model.dto.UserProfileDTO;
import com.onlikee.module.user.model.entity.UserEntity;
import com.onlikee.module.user.model.entity.UserProfileMarkdownEntity;

public interface UserProfileService {

    // 根据昵称查询公开用户资料。
    UserEntity getUserByNickname(String nickname);

    // 根据用户 uuid 查询公开资料页 Markdown 内容。
    UserProfileMarkdownEntity getMarkdownByUuid(String uuid);

    // 保存当前用户的 Markdown 资料页，uuid 只能来自登录态用户。
    void saveCurrentUserMarkdown(UserEntity user, UserProfileMarkdownDTO request);

    // 保存当前用户的公开基础资料，uuid 只能来自登录态用户。
    UserEntity saveCurrentUserProfile(UserEntity user, UserProfileDTO request);
}
