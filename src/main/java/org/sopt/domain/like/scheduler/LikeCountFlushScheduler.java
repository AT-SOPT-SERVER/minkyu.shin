package org.sopt.domain.like.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.comment.repository.CommentRepository;
import org.sopt.domain.like.cache.LikeCacheConstants;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.global.cache.RedisCacheRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeCountFlushScheduler {

    private final RedisCacheRepository redisCacheRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private static final String POST_KEY_PREFIX = LikeCacheConstants.POST_LIKE_KEY_PREFIX;
    private static final String COMMENT_KEY_PREFIX = LikeCacheConstants.COMMENT_LIKE_KEY_PREFIX;

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5분마다 실행
    public void flushLikeCountToDatabase() {
        flushPostLikeCounts();
        flushCommentLikeCounts();
    }

    private void flushPostLikeCounts() {
        Set<String> keys = redisCacheRepository.findKeys(POST_KEY_PREFIX + "*");
        for (String key : keys) {
            Long postId = extractId(key, POST_KEY_PREFIX);
            int likeCount = redisCacheRepository.get(key, Integer.class);
            postRepository.updateLikeCount(postId, likeCount);
            log.info("[Post] 좋아요 Flush - id: {}, count: {}", postId, likeCount);
        }
    }

    private void flushCommentLikeCounts() {
        Set<String> keys = redisCacheRepository.findKeys(COMMENT_KEY_PREFIX + "*");
        for (String key : keys) {
            Long commentId = extractId(key, COMMENT_KEY_PREFIX);
            int likeCount = redisCacheRepository.get(key, Integer.class);
            commentRepository.updateLikeCount(commentId, likeCount);
            log.info("[Comment] 좋아요 Flush - id: {}, count: {}", commentId, likeCount);
        }
    }

    private Long extractId(String key, String prefix) {
        try {
            return Long.parseLong(key.replace(prefix, ""));
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.INVALID_CACHE_KEY_FORMAT);
        }
    }
}