package org.sopt.domain.auth.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseMessage {

    SOCIAL_LOGIN_SUCCESS("소셜 로그인에 성공했습니다."),
    TOKEN_REISSUE_SUCCESS("토큰 재발급에 성공했습니다."),
    LOGOUT_SUCCESS("로그아웃에 성공했습니다.");

    private final String message;
}