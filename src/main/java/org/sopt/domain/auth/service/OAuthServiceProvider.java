package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.service.oauth.OAuthService;
import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthServiceProvider {

    private final List<OAuthService> socialServices;

    public OAuthService getService(SocialPlatform socialPlatform) {
        for (OAuthService oAuthService : socialServices) {
            if (oAuthService.support(socialPlatform)) {
                return oAuthService;
            }
        }
        throw new BusinessException(ErrorCode.UNSUPPORTED_SOCIAL_PLATFORM_EXCEPTION);
    }
}