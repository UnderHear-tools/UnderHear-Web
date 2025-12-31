package com.underhear.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.underhear.pojo.dto.response.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<Void>> handleBizException(BizException ex) {
        log.warn("Business exception: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponse.fail(ex.getCode(), ex.getMessage()));
    }

    // @ExceptionHandler(IllegalArgumentException.class)
    // public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
    //     log.warn("Bad request: {}", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //             .body(ApiResponse.fail(ErrorCode.BAD_REQUEST.getCode(), ex.getMessage()));
    // }

    // @ExceptionHandler(IllegalStateException.class)
    // public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(IllegalStateException ex) {
    //     log.warn("Illegal state: {}", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR.getCode(), ex.getMessage()));
    // }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }
}
