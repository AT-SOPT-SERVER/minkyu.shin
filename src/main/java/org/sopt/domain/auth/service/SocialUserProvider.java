package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.domain.*;
import org.sopt.domain.user.repository.SocialUserInfoRepository;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SocialUserProvider {

    private final UserRepository userRepository;
    private final SocialUserInfoRepository socialUserInfoRepository;

    @Transactional
    public User getUser(final SocialPlatform platform, final String socialId, final String email, final String nickname) {

        // 1. 동일한 소셜 계정 확인
        Optional<SocialUserInfo> existingSocial = socialUserInfoRepository
                .findBySocialPlatformAndSocialId(platform, socialId);

        if (existingSocial.isPresent()) {
            return existingSocial.get().getUser();
        }

        // 2. 이메일로 기존 유저 확인
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);

        if (user == null) {
            user = User.createSocialUser(nickname, email, UserRole.MEMBER);
            user = userRepository.save(user);
        }

        linkSocialAccount(user, platform, socialId);

        return user;
    }

    private void linkSocialAccount(final User user, final SocialPlatform platform, final String socialId) {
        SocialUserInfo socialInfo = SocialUserInfo.create(
                user,
                platform,
                socialId
        );

        socialUserInfoRepository.save(socialInfo);
    }
}