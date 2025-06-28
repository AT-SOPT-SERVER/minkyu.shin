package org.sopt.domain.auth.dto.response;


import org.sopt.global.security.jwt.dto.JwtTokenCollection;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse from(JwtTokenCollection jwtTokenCollection) {
        return new TokenResponse(
                jwtTokenCollection.getAccessToken(),
                jwtTokenCollection.getRefreshToken());
    }
}