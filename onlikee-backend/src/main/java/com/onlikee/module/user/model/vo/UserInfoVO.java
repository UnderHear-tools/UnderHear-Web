package com.onlikee.module.user.model.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private String uuid;
    private String nickname;
    private String email;
    private String avatarUrl;
    private String bio;
    private String pronoun;
    private String location;
    private String socialAccount0;
    private String socialAccount1;
    private String socialAccount2;
}
