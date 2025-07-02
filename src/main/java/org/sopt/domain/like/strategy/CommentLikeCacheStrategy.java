package org.sopt.domain.like.strategy;

import org.sopt.domain.like.cache.LikeCacheRepository;

public class CommentLikeCacheStrategy implements LikeCacheStrategy {
    @Override
    public Integer getCacheCount(final LikeCacheRepository repository, final Long targetId) {
        return repository.getCommentLikeCount(targetId);
    }

    @Override
    public void setCacheCount(final LikeCacheRepository repository, final Long targetId, final int count) {
        repository.setCommentLikeCount(targetId, count);
    }

    @Override
    public void deleteCache(final LikeCacheRepository repository, final Long targetId) {
        repository.deleteCommentLikeCount(targetId);
    }
}