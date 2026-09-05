package com.onlikee.user.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileMarkdownDTO {
    @NotNull(message = "content不能为空")
    private String content;
}
