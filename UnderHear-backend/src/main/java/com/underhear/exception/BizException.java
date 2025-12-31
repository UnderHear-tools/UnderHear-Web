package com.underhear.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;

public class BizException extends RuntimeException {
    private int code;
    @NonNull
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

    public BizException(int code, String message, @NonNull HttpStatusCode status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    @NonNull
    public HttpStatusCode getStatus() {
        return status;
    }
}
