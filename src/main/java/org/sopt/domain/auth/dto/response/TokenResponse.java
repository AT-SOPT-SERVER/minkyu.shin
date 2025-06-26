package org.sopt.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import org.sopt.global.jwt.dto.JwtTokenCollection;

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