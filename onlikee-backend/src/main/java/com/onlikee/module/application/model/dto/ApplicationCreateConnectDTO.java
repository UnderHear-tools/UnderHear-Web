package com.onlikee.module.application.model.dto;

import com.onlikee.common.util.UrlUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationCreateConnectDTO {
    @NotBlank(message = "appName不能为空")
    private String appName;

    @NotBlank(message = "appUrl不能为空")
    private String appUrl;

    @NotBlank(message = "visibility不能为空")
    private String visibility;

    @NotBlank(message = "appDescription不能为空")
    private String appDescription;

    @AssertTrue(message = "appUrl格式无效")
    public boolean isAppUrlValid() {
        if (appUrl == null || appUrl.isBlank()) {
            return true;
        }
        return UrlUtils.isUrl(appUrl);
    }
}
