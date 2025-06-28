package org.sopt.domain.auth.service.oauth;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.service.SocialUserProvider;
import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.domain.user.domain.User;
import org.sopt.global.feign.oauth.kakao.KakaoServerClient;
import org.sopt.global.security.jwt.JwtProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

    private final JwtProperty jwtProperty;
    private final KakaoServerClient kakaoServerClient;
    private final SocialUserProvider socialUserProvider;

    @Override
    @Transactional
    public User socialUserLogin(final String kakaoAccessToken) {
        var profile = kakaoServerClient.getUserInformation(
                jwtProperty.getBearerPrefix() + " " + kakaoAccessToken
        );
        return socialUserProvider.getUser(
                SocialPlatform.KAKAO,
                String.valueOf(profile.getId()),
                profile.getEmail(),
                profile.getNickname()
        );
    }

    @Override
    public boolean support(final SocialPlatform socialPlatform) {
        return socialPlatform == SocialPlatform.KAKAO;
    }

}