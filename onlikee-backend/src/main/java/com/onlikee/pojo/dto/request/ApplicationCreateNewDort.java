package com.onlikee.pojo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.onlikee.util.UrlUtils;

@Data
public class ApplicationCreateNewDort {
    @NotBlank(message = "framework不能为空")
    private String framework;

    private MultipartFile appFile;

    @NotBlank(message = "appName不能为空")
    private String appName;

    @NotBlank(message = "appUrl不能为空")
    private String appUrl;

    @NotBlank(message = "visibility不能为空")
    private String visibility;

    @NotBlank(message = "appDescription不能为空")
    private String appDescription;

    @AssertTrue(message = "appFile不能为空")
    public boolean isAppFileValid() {
        return appFile != null && !appFile.isEmpty();
    }

    @AssertTrue(message = "appUrl格式无效")
    public boolean isAppUrlValid() {
        if (appUrl == null || appUrl.isBlank()) {
            return true;
        }
        try {
            UrlUtils.extractOnlikeeAppUrlPrefix(appUrl);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
