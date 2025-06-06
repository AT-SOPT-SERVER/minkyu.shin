package org.sopt.domain.like.cache;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.sopt.domain.like.domain.LikeTargetType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LikeCacheConstants {

    public static final String POST_LIKE_KEY_PREFIX = "like:post:";
    public static final String COMMENT_LIKE_KEY_PREFIX = "like:comment:";

    public static String postLikeKey(Long postId) {
        return POST_LIKE_KEY_PREFIX + postId;
    }

    public static String commentLikeKey(Long commentId) {
        return COMMENT_LIKE_KEY_PREFIX + commentId;
    }

    public static String userLikeKey(Long userId, Long targetId, LikeTargetType targetType) {
        return String.format("like:%s:%d:user:%d", targetType.name().toLowerCase(), targetId, userId);
    }
}