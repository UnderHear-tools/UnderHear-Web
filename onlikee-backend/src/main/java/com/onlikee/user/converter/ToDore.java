package com.onlikee.user.converter;

import com.onlikee.user.model.dto.response.UserInfoDore;
import com.onlikee.user.model.dto.response.UserProfileDore;
import com.onlikee.user.model.dto.response.UserProfileMarkdownDore;
import com.onlikee.user.model.entity.User;
import com.onlikee.user.model.entity.UserProfileMarkdown;

public final class ToDore {

    private ToDore() {
    }

    public static UserInfoDore toUserInfoDore(User user) {
        UserInfoDore userInfoDore = new UserInfoDore();
        userInfoDore.setUuid(user.getUuid());
        userInfoDore.setNickname(user.getNickName());
        userInfoDore.setEmail(user.getEmail());
        userInfoDore.setAvatarUrl(user.getAvatarUrl());
        userInfoDore.setBio(user.getBio());
        userInfoDore.setPronoun(user.getPronoun());
        userInfoDore.setLocation(user.getLocation());
        userInfoDore.setSocialAccount0(user.getSocialAccount0());
        userInfoDore.setSocialAccount1(user.getSocialAccount1());
        userInfoDore.setSocialAccount2(user.getSocialAccount2());
        return userInfoDore;
    }

    public static UserProfileDore toUserProfileDore(User user) {
        UserProfileDore userProfileDore = new UserProfileDore();
        userProfileDore.setUuid(user.getUuid());
        userProfileDore.setNickname(user.getNickName());
        userProfileDore.setEmail(user.getEmail());
        userProfileDore.setAvatarUrl(user.getAvatarUrl());
        userProfileDore.setBio(user.getBio());
        userProfileDore.setPronoun(user.getPronoun());
        userProfileDore.setLocation(user.getLocation());
        userProfileDore.setSocialAccount0(user.getSocialAccount0());
        userProfileDore.setSocialAccount1(user.getSocialAccount1());
        userProfileDore.setSocialAccount2(user.getSocialAccount2());
        return userProfileDore;
    }

    public static UserProfileMarkdownDore toUserProfileMarkdownDore(UserProfileMarkdown markdown) {
        UserProfileMarkdownDore userProfileMarkdownDore = new UserProfileMarkdownDore();
        userProfileMarkdownDore.setMarkdown(markdown == null ? null : markdown.getContent());
        return userProfileMarkdownDore;
    }
}
