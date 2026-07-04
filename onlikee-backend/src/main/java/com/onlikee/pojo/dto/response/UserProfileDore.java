package com.onlikee.pojo.dto.response;

import lombok.Data;

@Data
public class UserProfileDore {
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
