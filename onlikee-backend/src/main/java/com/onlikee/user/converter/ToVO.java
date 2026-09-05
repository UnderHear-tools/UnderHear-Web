package com.onlikee.user.converter;

import com.onlikee.user.model.dto.UserInfoDTO;
import com.onlikee.user.model.vo.UserInfoVO;
import com.onlikee.user.model.vo.UserProfileVO;
import com.onlikee.user.model.vo.UserProfileMarkdownVO;
import com.onlikee.user.model.entity.UserEntity;
import com.onlikee.user.model.entity.UserProfileMarkdownEntity;

public final class ToVO {

    private ToVO() {
    }

    public static UserInfoVO toUserInfoVO(UserEntity user) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUuid(user.getUuid());
        userInfoVO.setNickname(user.getNickName());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setAvatarUrl(user.getAvatarUrl());
        userInfoVO.setBio(user.getBio());
        userInfoVO.setPronoun(user.getPronoun());
        userInfoVO.setLocation(user.getLocation());
        userInfoVO.setSocialAccount0(user.getSocialAccount0());
        userInfoVO.setSocialAccount1(user.getSocialAccount1());
        userInfoVO.setSocialAccount2(user.getSocialAccount2());
        return userInfoVO;
    }

    // 在响应边界把内部用户资料转换为前端展示结构。
    public static UserInfoVO toUserInfoVO(UserInfoDTO user) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUuid(user.getUuid());
        userInfoVO.setNickname(user.getNickname());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setAvatarUrl(user.getAvatarUrl());
        userInfoVO.setBio(user.getBio());
        userInfoVO.setPronoun(user.getPronoun());
        userInfoVO.setLocation(user.getLocation());
        userInfoVO.setSocialAccount0(user.getSocialAccount0());
        userInfoVO.setSocialAccount1(user.getSocialAccount1());
        userInfoVO.setSocialAccount2(user.getSocialAccount2());
        return userInfoVO;
    }

    public static UserProfileVO toUserProfileVO(UserEntity user) {
        UserProfileVO userProfileVO = new UserProfileVO();
        userProfileVO.setUuid(user.getUuid());
        userProfileVO.setNickname(user.getNickName());
        userProfileVO.setEmail(user.getEmail());
        userProfileVO.setAvatarUrl(user.getAvatarUrl());
        userProfileVO.setBio(user.getBio());
        userProfileVO.setPronoun(user.getPronoun());
        userProfileVO.setLocation(user.getLocation());
        userProfileVO.setSocialAccount0(user.getSocialAccount0());
        userProfileVO.setSocialAccount1(user.getSocialAccount1());
        userProfileVO.setSocialAccount2(user.getSocialAccount2());
        return userProfileVO;
    }

    public static UserProfileMarkdownVO toUserProfileMarkdownVO(UserProfileMarkdownEntity markdown) {
        UserProfileMarkdownVO userProfileMarkdownVO = new UserProfileMarkdownVO();
        userProfileMarkdownVO.setMarkdown(markdown == null ? null : markdown.getContent());
        return userProfileMarkdownVO;
    }
}
