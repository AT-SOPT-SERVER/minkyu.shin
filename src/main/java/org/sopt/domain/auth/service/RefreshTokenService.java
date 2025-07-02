package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.auth.domain.RefreshToken;
import org.sopt.domain.auth.repository.RefreshTokenRepository;
import org.sopt.domain.user.domain.User;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(User user, String token) {
        // 동시 접속 방지 및 토큰 갱신을 위해 기존 토큰 삭제
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        refreshTokenRepository.save(RefreshToken.create(user, token));
    }

    @Transactional(readOnly = true)
    public void validateStoredToken(User user, String token) {
        RefreshToken storedToken = refreshTokenRepository
                .findByUser(user)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REFRESH_TOKEN_EXCEPTION));

        if (!storedToken.getToken().equals(token)) {
            log.error("저장된 리프레시 토큰과 불일치 - userId: {}", user.getId());
            throw new BusinessException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }
    }
}