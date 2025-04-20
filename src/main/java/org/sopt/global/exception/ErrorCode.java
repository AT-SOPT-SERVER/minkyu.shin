package org.sopt.global.exception;


import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INPUT_BLANK_EXCEPTION(HttpStatus.BAD_REQUEST, "입력값이 비어있습니다."),
    NOT_EXIST_POST_EXCEPTION(HttpStatus.NOT_FOUND,"해당 게시물은 존재하지 않습니다."),
    DUPLICATED_TITLE_EXCEPTION(HttpStatus.CONFLICT, "중복된 제목은 사용하실 수 없습니다."),
    INVALID_TITLE_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "제목은 30자 이하로 작성해야 합니다."),
    POST_DELAY_EXCEPTION(HttpStatus.FORBIDDEN, "게시물 작성은 3분마다 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
