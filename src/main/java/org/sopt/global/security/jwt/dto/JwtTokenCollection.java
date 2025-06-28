package org.sopt.global.security.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenCollection {
    private final String accessToken;
    private final String refreshToken;

    public static JwtTokenCollection of(String accessToken, String refreshToken) {
        return new JwtTokenCollection(accessToken, refreshToken);
    }
}