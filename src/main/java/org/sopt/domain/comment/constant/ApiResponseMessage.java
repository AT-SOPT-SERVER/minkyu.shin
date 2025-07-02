package org.sopt.domain.comment.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseMessage {

    COMMENT_CREATED_SUCCESS("댓글을 성공적으로 등록했습니다."),
    COMMENT_UPDATED_SUCCESS("댓글을 성공적으로 수정했습니다."),
    COMMENT_DELETED_SUCCESS("댓글을 성공적으로 삭제했습니다.");

    private final String message;
}