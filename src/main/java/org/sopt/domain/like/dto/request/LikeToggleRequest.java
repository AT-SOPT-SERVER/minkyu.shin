package org.sopt.domain.like.dto.request;

import jakarta.validation.constraints.NotNull;
import org.sopt.domain.like.domain.LikeTargetType;

public record LikeToggleRequest(
        @NotNull LikeTargetType likeTargetType,

        @NotNull Long targetId
) {
}
