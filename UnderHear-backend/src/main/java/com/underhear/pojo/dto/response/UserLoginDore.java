package com.underhear.pojo.dto.response;
import lombok.Data;

@Data
public class UserLoginDore {
    private String uuid;
    private String nickname;
    private String avatarUrl;
    private String lastLoginSource;
}
