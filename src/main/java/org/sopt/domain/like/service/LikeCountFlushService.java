package org.sopt.domain.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.comment.repository.CommentRepository;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.global.cache.RedisCacheRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.sopt.domain.like.cache.LikeCacheConstants.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class LikeCountFlushService {

    private final RedisCacheRepository redisCacheRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void flushPostLikeCounts() {
        Set<String> keys = redisCacheRepository.findKeys(POST_LIKE_COUNT_KEY_PATTERN);
        for (String key : keys) {
            Long postId = extractId(key, POST_LIKE_COUNT_KEY_PREFIX);
            int likeCount = redisCacheRepository.get(key, Integer.class);
            postRepository.updateLikeCount(postId, likeCount);
            log.info("[Post] 좋아요 Flush - id: {}, count: {}", postId, likeCount);
        }
    }

    @Transactional
    public void flushCommentLikeCounts() {
        Set<String> keys = redisCacheRepository.findKeys(COMMENT_LIKE_COUNT_KEY_PATTERN);
        for (String key : keys) {
            Long commentId = extractId(key, COMMENT_LIKE_COUNT_KEY_PREFIX);
            int likeCount = redisCacheRepository.get(key, Integer.class);
            commentRepository.updateLikeCount(commentId, likeCount);
            log.info("[Comment] 좋아요 Flush - id: {}, count: {}", commentId, likeCount);
        }
    }

    private Long extractId(final String key, final String prefix) {
        try {
            return Long.parseLong(key.replace(prefix, ""));
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.INVALID_CACHE_KEY_FORMAT);
        }
    }
}