package org.sopt.domain.like.cache;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.domain.LikeTargetType;
import org.sopt.global.cache.RedisCacheRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

import static org.sopt.domain.like.cache.LikeCacheConstants.*;

@Repository
@RequiredArgsConstructor
public class LikeCacheRepository {

    private final RedisCacheRepository redisCacheRepository;

    /**
     * 게시글 좋아요
     */
    public Integer getPostLikeCount(Long postId) {
        return redisCacheRepository.get(postLikeKey(postId), Integer.class);
    }

    public void setPostLikeCount(Long postId, int count) {
        redisCacheRepository.set(postLikeKey(postId), count, 15, TimeUnit.MINUTES);
    }

    public void deletePostLikeCount(Long postId) {
        redisCacheRepository.delete(postLikeKey(postId));
    }

    /**
     * 댓글 좋아요
     */
    public Integer getCommentLikeCount(Long commentId) {
        return redisCacheRepository.get(commentLikeKey(commentId), Integer.class);
    }

    public void setCommentLikeCount(Long commentId, int count) {
        redisCacheRepository.set(commentLikeKey(commentId), count, 15, TimeUnit.MINUTES);
    }

    public void deleteCommentLikeCount(Long commentId) {
        redisCacheRepository.delete(commentLikeKey(commentId));
    }

    /**
     * 좋아요 상태
     */
    public void setUserLikedStatus(Long userId, Long targetId, LikeTargetType targetType, boolean liked) {
        String key = userLikeKey(userId, targetId, targetType);
        redisCacheRepository.set(key, liked);
    }

    public Boolean getUserLikedStatus(Long userId, Long targetId, LikeTargetType targetType) {
        String key = userLikeKey(userId, targetId, targetType);
        return redisCacheRepository.get(key, Boolean.class);
    }
}