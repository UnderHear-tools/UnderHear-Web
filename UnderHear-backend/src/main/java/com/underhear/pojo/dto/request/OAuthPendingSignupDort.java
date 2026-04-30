package com.underhear.pojo.dto.request;

import lombok.Data;

@Data
// 保存 OAuth 已确认但尚未创建本系统用户的短期注册资料。
public class OAuthPendingSignupDort {
    private String provider;
    private Long providerUserId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String providerToken;
}
