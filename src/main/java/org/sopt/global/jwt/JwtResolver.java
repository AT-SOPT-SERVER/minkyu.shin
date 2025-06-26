package org.sopt.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.jwt.JwtProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtResolver {

    private final JwtProperty jwtProperty;
    private Key accessKey;
    private Key refreshKey;

    @PostConstruct
    public void init() {
        byte[] accessKeyBytes = jwtProperty.getAccessKey().getBytes(StandardCharsets.UTF_8);
        byte[] refreshKeyBytes = jwtProperty.getRefreshKey().getBytes(StandardCharsets.UTF_8);
        accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    // 예외를 그대로 전파 - Filter에서 처리
    public Long getUserIdFromAccessToken(String accessToken) {
        Claims claims = getAccessTokenClaims(accessToken);
        return Long.parseLong(claims.get("userId").toString());
    }

    public Long getUserIdFromRefreshToken(String refreshToken) {
        Claims claims = getRefreshTokenClaims(refreshToken);
        return Long.parseLong(claims.get("userId").toString());
    }

    private Claims getAccessTokenClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(accessKey.getEncoded()))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    private Claims getRefreshTokenClaims(String refreshToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(refreshKey.getEncoded()))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
    }


}