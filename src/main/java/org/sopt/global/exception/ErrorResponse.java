package org.sopt.global.exception;

import org.springframework.http.ResponseEntity;

public record ErrorResponse(String message) {

    public static ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.from(errorCode));
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getMessage());
    }
}
