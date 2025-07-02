package org.sopt.domain.auth.service.oauth;

import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.domain.user.domain.User;

public interface OAuthService {

    boolean support(SocialPlatform socialPlatform);

    User socialUserLogin(String oauthAccessToken);

}