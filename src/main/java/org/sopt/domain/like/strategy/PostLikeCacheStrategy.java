package org.sopt.domain.like.strategy;

import org.sopt.domain.like.cache.LikeCacheRepository;

public class PostLikeCacheStrategy implements LikeCacheStrategy {
    @Override
    public Integer getCacheCount(LikeCacheRepository repository, Long targetId) {
        return repository.getPostLikeCount(targetId);
    }

    @Override
    public void setCacheCount(LikeCacheRepository repository, Long targetId, int count) {
        repository.setPostLikeCount(targetId, count);
    }

    @Override
    public void deleteCache(LikeCacheRepository repository, Long targetId) {
        repository.deletePostLikeCount(targetId);
    }
}