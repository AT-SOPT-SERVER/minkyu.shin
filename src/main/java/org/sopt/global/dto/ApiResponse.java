package org.sopt.global.dto;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
    // 성공 응답 (데이터 없음)
    public static ResponseEntity<ApiResponse<Void>> ok(HttpStatus httpStatus, String message) {
        ApiResponse<Void> body = new ApiResponse<>(
                httpStatus.value(),
                message,
                null
        );
        return ResponseEntity.status(httpStatus).body(body);
    }

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> ok(HttpStatus httpStatus, String message, T data) {

        ApiResponse<T> body = new ApiResponse<>(
                httpStatus.value(),
                message,
                data
        );

        return ResponseEntity.status(httpStatus).body(body);
    }

    // 에러 응답
    public static ResponseEntity<ApiResponse<Void>> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.from(errorCode));
    }

    public static ApiResponse<Void> from(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
    }

    public static ApiResponse<Void> of(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(),
                errorCode.getMessage() + "- detail message: " + message, null);
    }

}