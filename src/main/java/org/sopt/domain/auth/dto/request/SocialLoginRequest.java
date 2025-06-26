package org.sopt.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocialLoginRequest(
        @NotBlank String oauthAccessToken
) {
}
