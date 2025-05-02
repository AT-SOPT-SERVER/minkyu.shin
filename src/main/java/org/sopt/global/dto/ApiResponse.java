package org.sopt.global.dto;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
    // 성공 응답 (200 OK)
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "요청이 성공했습니다.", data);
    }

    // 생성 응답 (201 Created)
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "리소스가 생성되었습니다.", data);
    }

    // 에러 응답
    public static ResponseEntity<ApiResponse<Void>> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.from(errorCode));
    }

    public static ApiResponse<Void> from(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
    }

}