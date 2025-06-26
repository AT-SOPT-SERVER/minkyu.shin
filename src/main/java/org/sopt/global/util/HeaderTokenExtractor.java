package org.sopt.global.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.jwt.JwtProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderTokenExtractor {

    private final JwtProperty jwtProperty;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

    public String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        checkValidBearerToken(AUTHORIZATION_HEADER, authorizationHeader);

        String bearerPrefix = jwtProperty.getBearerPrefix() + " ";
        return authorizationHeader.substring(bearerPrefix.length());
    }

    public String extractRefreshToken(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader(REFRESH_TOKEN_HEADER);
        log.info("extractRefreshToken 호출, Refresh-Token 헤더 값: {}", refreshTokenHeader);
        checkValidBearerToken(REFRESH_TOKEN_HEADER, refreshTokenHeader);

        String bearerPrefix = jwtProperty.getBearerPrefix() + " ";
        log.info("extractRefreshToken: {}", refreshTokenHeader.substring(bearerPrefix.length()));
        return refreshTokenHeader.substring(bearerPrefix.length());
    }

    private void checkValidBearerToken(String headerName, String bearerToken) {
        if (!isValidBearerToken(bearerToken)) {
            log.error("{} 헤더가 \"Bearer\"로 시작하지 않습니다 : [{}]", headerName, bearerToken);
            throw new BusinessException(ErrorCode.NOT_FOUND_BEARER_PREFIX_EXCEPTION);
        }
    }

    private boolean isValidBearerToken(String bearerToken) {
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperty.getBearerPrefix());
    }
}
