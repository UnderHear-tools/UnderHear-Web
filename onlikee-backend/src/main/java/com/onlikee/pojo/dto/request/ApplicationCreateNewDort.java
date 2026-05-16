package com.onlikee.pojo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationCreateNewDort {
    @NotBlank(message = "framework不能为空")
    private String framework;

    private MultipartFile appFile;

    @NotBlank(message = "appName不能为空")
    private String appName;

    @NotBlank(message = "appEnglishName不能为空")
    private String appEnglishName;

    @NotBlank(message = "visibility不能为空")
    private String visibility;

    @NotBlank(message = "appDescription不能为空")
    private String appDescription;

    @AssertTrue(message = "appFile不能为空")
    public boolean isAppFileValid() {
        return appFile != null && !appFile.isEmpty();
    }
}
