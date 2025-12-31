package com.underhear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull; 

public enum ErrorCode {
    BAD_REQUEST(400, "bad request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR(500, "internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    @NonNull
    private final HttpStatusCode status;

    ErrorCode(int code, String message,@NonNull HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    public HttpStatusCode getStatus() {
        return status;
    }
}
