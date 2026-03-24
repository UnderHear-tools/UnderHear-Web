package com.underhear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {

    // 通用错误
    BAD_REQUEST("BAD_REQUEST", "请求错误", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILED("VALIDATION_FAILED", "参数校验失败", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("UNAUTHORIZED", "未授权", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "禁止访问", HttpStatus.FORBIDDEN),
    NOT_FOUND("NOT_FOUND", "未找到资源", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("INTERNAL_ERROR", "服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR),

    // 业务错误
    USER_NOT_FOUND("USER_NOT_FOUND", "未找到该用户", HttpStatus.NOT_FOUND),
    BAD_AUTHORIZED("BAD_AUTHORIZED", "授权失败", HttpStatus.UNAUTHORIZED),
    APP_ENGLISH_NAME_ALREADY_EXISTS("APP_ENGLISH_NAME_ALREADY_EXISTS", "应用英文名称已存在", HttpStatus.CONFLICT),
    APPLICATION_CREATE_FAILED("APPLICATION_CREATE_FAILED", "应用创建失败", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_LOGIN("NOT_LOGIN", "未登录或登录已过期", HttpStatus.OK),

    // 兜底错误
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatusCode status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
