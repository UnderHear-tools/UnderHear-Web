package com.onlikee.module.user.converter;

import com.onlikee.module.user.model.dto.UserInfoDTO;
import com.onlikee.module.user.model.entity.UserEntity;

// 用户资料在服务之间传递时使用 DTO，避免耦合前端展示模型。
public final class ToDTO {

    private ToDTO() {
    }

    public static UserInfoDTO toUserInfoDTO(UserEntity user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUuid(user.getUuid());
        userInfoDTO.setNickname(user.getNickName());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setAvatarUrl(user.getAvatarUrl());
        userInfoDTO.setBio(user.getBio());
        userInfoDTO.setPronoun(user.getPronoun());
        userInfoDTO.setLocation(user.getLocation());
        userInfoDTO.setSocialAccount0(user.getSocialAccount0());
        userInfoDTO.setSocialAccount1(user.getSocialAccount1());
        userInfoDTO.setSocialAccount2(user.getSocialAccount2());
        return userInfoDTO;
    }
}
