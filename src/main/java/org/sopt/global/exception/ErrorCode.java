package org.sopt.global.exception;


import org.springframework.http.HttpStatus;

public enum ErrorCode {

    POST_TITLE_BLANK_EXCEPTION(HttpStatus.BAD_REQUEST, "빈 제목은 허용되지 않습니다."),
    NOT_EXIST_POST_EXCEPTION(HttpStatus.NOT_FOUND,"해당 게시물은 존재하지 않습니다."),
    DUPLICATED_TITLE_EXCEPTION(HttpStatus.CONFLICT, "중복된 제목은 사용하실 수 없습니다."),
    INVALID_TITLE_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "제목은 30자 이하로 작성해야 합니다."),;

    private final HttpStatus httpStatus;
    private final String msg;

    ErrorCode(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public String getMsg() {
        return msg;
    }
}
