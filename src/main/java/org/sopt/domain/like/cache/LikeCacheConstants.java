package org.sopt.domain.like.cache;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.sopt.domain.like.domain.LikeTargetType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LikeCacheConstants {

    public static final String POST_LIKE_COUNT_KEY_PREFIX = "likeCount:post:";
    public static final String COMMENT_LIKE_COUNT_KEY_PREFIX = "likeCount:comment:";
    public static final String POST_LIKE_COUNT_KEY_PATTERN = "likeCount:post:*";
    public static final String COMMENT_LIKE_COUNT_KEY_PATTERN = "likeCount:comment:*";

    public static final String LIKE_STATUS_KEY_PREFIX= "likeStatus:";
    public static final String LIKE_STATUS_KEY_PATTERN = "likeStatus:*:user:*";


    public static String postLikeKey(Long postId) {
        return POST_LIKE_COUNT_KEY_PREFIX + postId;
    }

    public static String commentLikeKey(Long commentId) {
        return COMMENT_LIKE_COUNT_KEY_PREFIX + commentId;
    }

    public static String userLikeKey(Long userId, Long targetId, LikeTargetType targetType) {
        return LIKE_STATUS_KEY_PREFIX + targetType.name().toLowerCase() + ":" + targetId + ":user:" + userId;
    }
}