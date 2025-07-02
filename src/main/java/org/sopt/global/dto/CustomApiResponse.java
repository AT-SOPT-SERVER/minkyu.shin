package org.sopt.global.dto;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record CustomApiResponse<T>(
        int status,
        String message,
        T data
) {
    // 성공 응답 (데이터 없음)
    public static ResponseEntity<CustomApiResponse<Void>> ok(HttpStatus httpStatus, String message) {
        CustomApiResponse<Void> body = new CustomApiResponse<>(
                httpStatus.value(),
                message,
                null
        );
        return ResponseEntity.status(httpStatus).body(body);
    }

    // 성공 응답
    public static <T> ResponseEntity<CustomApiResponse<T>> ok(HttpStatus httpStatus, String message, T data) {

        CustomApiResponse<T> body = new CustomApiResponse<>(
                httpStatus.value(),
                message,
                data
        );

        return ResponseEntity.status(httpStatus).body(body);
    }

    // 에러 응답
    public static ResponseEntity<CustomApiResponse<Void>> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(CustomApiResponse.from(errorCode));
    }

    public static CustomApiResponse<Void> from(ErrorCode errorCode) {
        return new CustomApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
    }

    public static CustomApiResponse<Void> of(ErrorCode errorCode, String message) {
        return new CustomApiResponse<>(errorCode.getHttpStatus().value(),
                errorCode.getMessage() + "- detail message: " + message, null);
    }

}