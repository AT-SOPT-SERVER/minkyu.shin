package org.sopt.global.security.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.sopt.global.security.jwt.dto.JwtPayload;
import org.sopt.global.security.jwt.dto.JwtTokenCollection;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperty jwtProperty;
    private Key accessKey;
    private Key refreshKey;
    private Integer accessExpired;
    private Integer refreshExpired;

    @PostConstruct
    public void init() {
        byte[] accessKeyBytes = jwtProperty.getAccessKey().getBytes(StandardCharsets.UTF_8);
        byte[] refreshKeyBytes = jwtProperty.getRefreshKey().getBytes(StandardCharsets.UTF_8);
        accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
        accessExpired = jwtProperty.getAccessExpiredMin();
        refreshExpired = jwtProperty.getRefreshExpiredDay();
    }

    public JwtTokenCollection createTokenCollection(JwtPayload jwtPayload) {
        return JwtTokenCollection.of(
                createAccessToken(jwtPayload),
                createRefreshToken(jwtPayload)
        );
    }

    public String createAccessToken(JwtPayload jwtPayload) {
        return Jwts.builder()
                .setSubject("access_token")
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(jwtPayload.getPayload())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(accessExpired, ChronoUnit.MINUTES)))
                .signWith(accessKey)
                .compact();
    }

    public String createRefreshToken(JwtPayload jwtPayload) {
        return Jwts.builder()
                .setSubject("refresh_token")
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(jwtPayload.getPayload())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpired, ChronoUnit.DAYS)))
                .signWith(refreshKey)
                .compact();
    }
}