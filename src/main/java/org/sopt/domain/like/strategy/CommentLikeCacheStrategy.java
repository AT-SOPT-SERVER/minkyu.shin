package org.sopt.domain.like.strategy;

import org.sopt.domain.like.cache.LikeCacheRepository;

public class CommentLikeCacheStrategy implements LikeCacheStrategy {
    @Override
    public Integer getCacheCount(LikeCacheRepository repository, Long targetId) {
        return repository.getCommentLikeCount(targetId);
    }

    @Override
    public void setCacheCount(LikeCacheRepository repository, Long targetId, int count) {
        repository.setCommentLikeCount(targetId, count);
    }

    @Override
    public void deleteCache(LikeCacheRepository repository, Long targetId) {
        repository.deleteCommentLikeCount(targetId);
    }
}