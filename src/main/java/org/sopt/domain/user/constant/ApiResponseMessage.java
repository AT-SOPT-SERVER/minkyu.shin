package org.sopt.domain.user.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiResponseMessage {

    USER_CREATED("회원가입에 성공했습니다.");

    private final String message;
}
