package org.sopt.domain.auth.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseMessage {

    LOGIN_SUCCESS("로그인에 성공했습니다.");

    private final String message;
}