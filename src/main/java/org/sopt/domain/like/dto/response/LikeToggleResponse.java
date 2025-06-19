package org.sopt.domain.like.dto.response;

public record LikeToggleResponse(
        boolean isLiked
) {
    public static LikeToggleResponse from(boolean isLiked) {
        return new LikeToggleResponse(isLiked);
    }
}
