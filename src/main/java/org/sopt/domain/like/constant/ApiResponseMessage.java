package org.sopt.domain.like.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseMessage {

    LIKE_STATUS_TOGGLED_SUCCESS("좋아요 상태가 변경되었습니다.");

    private final String message;
}