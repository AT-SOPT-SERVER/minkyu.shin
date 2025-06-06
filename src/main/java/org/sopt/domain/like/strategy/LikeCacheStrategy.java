package org.sopt.domain.like.strategy;

import org.sopt.domain.like.cache.LikeCacheRepository;

public interface LikeCacheStrategy {
    Integer getCacheCount(LikeCacheRepository repository, Long targetId);
    void setCacheCount(LikeCacheRepository repository, Long targetId, int count);
    void deleteCache(LikeCacheRepository repository, Long targetId);
}
