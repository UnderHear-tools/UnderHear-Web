package com.onlikee.auth.oauth.model.dto.request;

import lombok.Data;

@Data
public class UserGiteeDort {
    private Long giteeId;
    private String name;
    private String avatarUrl;
    private String email;
    private String bio;
    private String htmlUrl;
    private String giteeToken;
}
