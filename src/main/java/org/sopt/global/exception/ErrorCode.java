package org.sopt.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.constant.CommentPolicyConstant;
import org.sopt.domain.post.constant.PostPolicyConstant;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * post. code prefix: post-
     */
    INPUT_BLANK_EXCEPTION(HttpStatus.BAD_REQUEST, "post-1", "입력값은 null 또는 빈 값일 수 없습니다."),
    NOT_EXIST_POST_EXCEPTION(HttpStatus.NOT_FOUND, "post-2", "해당 게시물은 존재하지 않습니다."),
    DUPLICATED_TITLE_EXCEPTION(HttpStatus.CONFLICT, "post-3", "중복된 제목은 사용하실 수 없습니다."),
    INVALID_TITLE_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "post-4",
            "제목은 " + PostPolicyConstant.TITLE_MAX_LENGTH.getValue() + "자 이하로 작성해야 합니다."),
    INVALID_CONTENT_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "post-5",
            "본문은 " + PostPolicyConstant.CONTENT_MAX_LENGTH.getValue() + "자 이하로 작성해야 합니다."),
    POST_DELAY_EXCEPTION(HttpStatus.FORBIDDEN, "post-5",
            "게시물 작성은 " + PostPolicyConstant.POST_DELAY_SECONDS.getValue() + "초마다 가능합니다."),
    INVALID_POST_TAG_EXCEPTION(HttpStatus.BAD_REQUEST, "post-6", "해당 태그는 잘못된 태그입니다."),
    INVALID_COMMENT_LENGTH_EXCEPTION(HttpStatus.BAD_REQUEST, "post-7",
            "댓글은 " + CommentPolicyConstant.COMMENT_MAX_LENGTH.getValue() + "자 이하로 작성해야 합니다."),
    NOT_SUPPORTED_SORT_TYPE(HttpStatus.BAD_REQUEST, "post-8" , "지원하지 않는 페이징 정렬 기준입니다." ),
    NOT_SUPPORTED_SEARCH_TYPE(HttpStatus.BAD_REQUEST, "post-9" , "지원하지 않는 검색 기준입니다." ),
    TAG_COUNT_LIMIT_EXCEPTION(HttpStatus.BAD_REQUEST, "post-10" , "최대 2개까지의 태그까지만 지정 가능합니다." ),


    /**
     * auth. code prefix: auth-
     */
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-1", "인증되지 않은 사용자입니다."),
    EXPIRED_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-2", "만료된 엑세스 토큰입니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-3", "만료된 리프레시 토큰입니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-4", "유효하지 않은 엑세스 토큰입니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-5", "유효하지 않은 리프레시 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "auth-6", "지원하지 않는 JWT 토큰입니다."),


    /**
     * resource. code prefix: resource-
     */
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "auth-1", "리소스에 대한 접근 권한이 없습니다."),


    /**
     * common. code prefix: common-
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "auth-1", "서버 내부에서 오류가 발생했습니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "auth-2", "입력값이 올바르지 않습니다."),
    NOT_FOUND_RESOURCE_EXCEPTION(HttpStatus.NOT_FOUND, "common-3", "존재하지 않는 데이터입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "common-4", "지원하지 않는 HTTP 메소드입니다."),
    INVALID_CACHE_KEY_FORMAT(HttpStatus.BAD_REQUEST, "common-5", "캐시 키 형식이 잘못되었습니다."),
    REDIS_OPERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "redis-1", "Redis 처리 중 오류가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
