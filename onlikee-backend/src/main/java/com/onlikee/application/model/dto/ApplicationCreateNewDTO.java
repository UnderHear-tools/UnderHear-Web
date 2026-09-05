package com.onlikee.application.model.dto;

import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationCreateNewDTO {
    @NotBlank(message = "framework不能为空")
    private String framework;

    private MultipartFile appFile;

    @NotBlank(message = "appName不能为空")
    private String appName;

    @NotBlank(message = "appSubDomain不能为空")
    private String appSubDomain;

    @NotBlank(message = "visibility不能为空")
    private String visibility;

    @NotBlank(message = "appDescription不能为空")
    private String appDescription;

    @AssertTrue(message = "appFile不能为空")
    public boolean isAppFileValid() {
        return appFile != null && !appFile.isEmpty();
    }

    @AssertTrue(message = "appSubDomain格式无效")
    public boolean isAppSubDomainValid() {
        if (appSubDomain == null || appSubDomain.isBlank()) {
            return true;
        }
        return appSubDomain.length() <= 63
                && appSubDomain.matches("[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    }

    public void setAppSubDomain(String appSubDomain) {
        this.appSubDomain = appSubDomain == null
                ? null
                : appSubDomain.trim().toLowerCase(Locale.ROOT);
    }
}
