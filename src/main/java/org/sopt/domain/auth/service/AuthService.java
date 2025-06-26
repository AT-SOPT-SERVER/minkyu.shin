package org.sopt.domain.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.auth.dto.request.SocialLoginRequest;
import org.sopt.domain.auth.dto.response.TokenResponse;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.sopt.domain.auth.service.oauth.OAuthService;
import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserStatus;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.jwt.JwtProvider;
import org.sopt.global.jwt.JwtResolver;
import org.sopt.global.jwt.dto.JwtPayload;
import org.sopt.global.jwt.dto.JwtTokenCollection;
import org.sopt.global.util.HeaderTokenExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final OAuthServiceProvider oAuthServiceProvider;
    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public TokenResponse socialLogin(SocialPlatform socialPlatform, SocialLoginRequest socialLoginRequest) {
        OAuthService oAuthService = oAuthServiceProvider.getService(socialPlatform);
        User user = oAuthService.socialUserLogin(socialLoginRequest.oauthAccessToken());

        JwtTokenCollection tokens = createNewTokens(user);

        refreshTokenService.saveRefreshToken(user, tokens.getRefreshToken());

        return TokenResponse.from(tokens);
    }

    public TokenResponse reissue(final String refreshToken) {
        // 1. 리프레시 토큰에서 사용자 정보 추출
        Long userId = jwtResolver.getUserIdFromRefreshToken(refreshToken);

        // 2. 사용자 검증
        User user = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        // 3. DB에 저장된 리프레시 토큰과 비교
        refreshTokenService.validateStoredToken(user, refreshToken);



        return TokenResponse.from(createNewTokens(user));
    }

    @Transactional
    public void logout(final Long userId) {
        User user = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        refreshTokenRepository.deleteByUser(user);
        log.info("로그아웃 완료 - userId: {}", userId);
    }

    private JwtTokenCollection createNewTokens(User user) {
        return jwtProvider.createTokenCollection(JwtPayload.from(user));
    }
}