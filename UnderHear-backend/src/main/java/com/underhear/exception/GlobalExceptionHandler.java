package com.underhear.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.underhear.pojo.dto.response.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理业务代码主动抛出的自定义异常。
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<Void>> handleBizException(BizException ex) {
        log.warn("Business exception: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponse.fail(ex.getCode(), ex.getMessage()));
    }

    // 处理参数绑定失败和 @Valid 校验失败，统一返回参数校验错误。
    @ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception ex) {
        String message = ErrorCode.VALIDATION_FAILED.getMessage();
        log.warn("Validation failed: {}", message);
        return ResponseEntity.status(ErrorCode.VALIDATION_FAILED.getStatus())
                .body(ApiResponse.fail(ErrorCode.VALIDATION_FAILED.getCode(), message));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        String message = ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getMessage();
        log.warn("Upload size exceeded: {}", ex.getMessage());
        return ResponseEntity.status(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getStatus())
                .body(ApiResponse.fail(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getCode(), message));
    }

    // 兜底处理未被前面捕获的异常，统一返回服务器内部错误。
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }
}
