package org.sopt.global.exception;


import org.sopt.domain.post.constant.PostPolicyConstant;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    //business
    INPUT_BLANK_EXCEPTION(HttpStatus.BAD_REQUEST, "입력값이 비어있습니다."),
    NOT_EXIST_POST_EXCEPTION(HttpStatus.NOT_FOUND,"해당 게시물은 존재하지 않습니다."),
    DUPLICATED_TITLE_EXCEPTION(HttpStatus.CONFLICT, "중복된 제목은 사용하실 수 없습니다."),
    INVALID_TITLE_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST,
            "제목은 " + PostPolicyConstant.TITLE_MAX_LENGTH.getValue() + "자 이하로 작성해야 합니다."),
    INVALID_CONTENT_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST,
            "본문은 " + PostPolicyConstant.CONTENT_MAX_LENGTH.getValue() + "자 이하로 작성해야 합니다."),
    POST_DELAY_EXCEPTION(HttpStatus.FORBIDDEN, "게시물 작성은 "
            + PostPolicyConstant.POST_DELAY_SECONDS.getValue() + "초마다 가능합니다."),

    //common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 오류가 발생했습니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    NOT_FOUND_RESOURCE_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 데이터입니다.");


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
