package org.sopt.domain.like.strategy;

import org.sopt.domain.like.cache.LikeCacheRepository;

public interface LikeCacheStrategy {
    Integer getCacheCount(final LikeCacheRepository repository, final Long targetId);
    void setCacheCount(final LikeCacheRepository repository, final Long targetId, final int count);
    void deleteCache(final LikeCacheRepository repository, final Long targetId);
}
