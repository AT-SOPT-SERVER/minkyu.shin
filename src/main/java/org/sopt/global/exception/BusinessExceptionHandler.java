package org.sopt.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.error("BusinessException : {}", e.getErrorCode().getMessage(), e);
        return ApiResponse.createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(Exception e) {
        log.error("Bad Request Exception: {}", e.getMessage(), e);
        return ApiResponse.createErrorResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("NoResourceFoundException: {}", e.getMessage(), e);
        return ApiResponse.createErrorResponseEntity(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.warn("MethodNotAllowedException: {}", e.getMessage(), e);
        return ApiResponse.createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    // JWT 관련 예외 처리
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("JWT 토큰 만료", e);
        return ApiResponse.createErrorResponseEntity(
                ErrorCode.EXPIRED_TOKEN_EXCEPTION
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtException(JwtException e) {
        log.error("JWT 처리 오류", e);
        return ApiResponse.createErrorResponseEntity(
                ErrorCode.INVALID_TOKEN_EXCEPTION
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandledException(Exception e) {
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        return ApiResponse.createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
