package com.onlikee.pojo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileMarkdownDort {
    @NotNull(message = "content不能为空")
    private String content;
}
