package org.sopt.domain.post.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseMessage {

    POST_CREATED_SUCCESS("게시글이 생성되었습니다."),
    POST_UPDATED_SUCCESS("게시글이 수정되었습니다."),
    POST_DELETED_SUCCESS("게시글이 삭제되었습니다."),
    POST_GET_SUCCESS("게시글 조회에 성공했습니다."),
    POST_DETAILS_GET_SUCCESS("게시글 상세 조회에 성공했습니다.");

    private final String message;
}