package com.onlikee.common.exception;

import org.springframework.http.HttpStatusCode;

public class BizException extends RuntimeException {
    private String code;
    private HttpStatusCode status;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }

    public BizException(String code, String message, HttpStatusCode status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
