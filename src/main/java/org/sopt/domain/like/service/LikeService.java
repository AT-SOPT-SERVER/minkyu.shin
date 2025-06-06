package org.sopt.domain.like.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.cache.LikeCacheRepository;
import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeTargetType;
import org.sopt.domain.like.dto.request.LikeToggleRequest;
import org.sopt.domain.like.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Transactional
    public void toggleLike(Long userId, @Valid LikeToggleRequest request) {
        Long targetId = request.targetId();
        LikeTargetType likeTargetType = request.likeTargetType();

        Optional<Like> like = likeRepository.findByUserIdAndLikeTargetAndTargetId(userId, likeTargetType, targetId);

        if (like.isPresent()) {
            likeRepository.delete(like.get());
            decrementLikeCount(targetId, likeTargetType);
        } else {
            likeRepository.save(Like.create(userId, targetId, likeTargetType));
            incrementLikeCount(targetId, likeTargetType);
        }
    }

    private void incrementLikeCount(Long targetId, LikeTargetType likeTargetType) {
        Integer count = likeTargetType.getCacheCount(likeCacheRepository, targetId);
        if (count == null) {
            count = likeRepository.countByTargetIdAndLikeTarget(targetId, likeTargetType);
        }
        likeTargetType.setCacheCount(likeCacheRepository, targetId, count + 1);
    }

    private void decrementLikeCount(Long targetId, LikeTargetType likeTargetType) {
        Integer count = likeTargetType.getCacheCount(likeCacheRepository, targetId);
        if (count == null || count <= 1) {
            likeTargetType.deleteCache(likeCacheRepository, targetId);
        } else {
            likeTargetType.setCacheCount(likeCacheRepository, targetId, count - 1);
        }
    }
}