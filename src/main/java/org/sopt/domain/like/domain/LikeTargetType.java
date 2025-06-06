package org.sopt.domain.like.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.cache.LikeCacheRepository;
import org.sopt.domain.like.strategy.CommentLikeCacheStrategy;
import org.sopt.domain.like.strategy.LikeCacheStrategy;
import org.sopt.domain.like.strategy.PostLikeCacheStrategy;

/**
 * 타입별(POST, COMMENT)로 서로 다른 캐시 처리 로직을 실행할 수 있도록
 */
@RequiredArgsConstructor
public enum LikeTargetType {
    POST(new PostLikeCacheStrategy()),
    COMMENT(new CommentLikeCacheStrategy());

    private final LikeCacheStrategy strategy;

    public Integer getCacheCount(LikeCacheRepository repository, Long targetId) {
        return strategy.getCacheCount(repository, targetId);
    }

    public void setCacheCount(LikeCacheRepository repository, Long targetId, int count) {
        strategy.setCacheCount(repository, targetId, count);
    }

    public void deleteCache(LikeCacheRepository repository, Long targetId) {
        strategy.deleteCache(repository, targetId);
    }
}