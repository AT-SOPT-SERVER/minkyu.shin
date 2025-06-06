package org.sopt.domain.like.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.cache.LikeCacheRepository;
import org.sopt.domain.like.domain.LikeTargetType;
import org.sopt.domain.like.dto.request.LikeToggleRequest;
import org.sopt.domain.like.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeCacheRepository likeCacheRepository;

    @Transactional
    public void toggleLike(final Long userId, final LikeToggleRequest request) {
        Long targetId = request.targetId();
        LikeTargetType targetType = request.likeTargetType();

        boolean liked = getUserLikedStatus(userId, targetId, targetType);

        if (liked) {
            likeCacheRepository.setUserLikedStatus(userId, targetId, targetType, false);
            decrementLikeCount(targetId, targetType);
        } else {
            likeCacheRepository.setUserLikedStatus(userId, targetId, targetType, true);
            incrementLikeCount(targetId, targetType);
        }
    }

    private void incrementLikeCount(final Long targetId, final LikeTargetType likeTargetType) {
        Integer count = likeTargetType.getCacheCount(likeCacheRepository, targetId);
        if (count == null) {
            count = likeRepository.countByTargetIdAndLikeTargetType(targetId, likeTargetType);
        }
        likeTargetType.setCacheCount(likeCacheRepository, targetId, count + 1);
    }

    private void decrementLikeCount(final Long targetId, final LikeTargetType likeTargetType) {
        Integer count = likeTargetType.getCacheCount(likeCacheRepository, targetId);
        if (count == null || count <= 1) {
            likeTargetType.deleteCache(likeCacheRepository, targetId);
        } else {
            likeTargetType.setCacheCount(likeCacheRepository, targetId, count - 1);
        }
    }

    public boolean getUserLikedStatus(final Long userId, final Long targetId, final LikeTargetType targetType) {
        boolean cached = likeCacheRepository.getUserLikedStatus(userId, targetId, targetType);

        if (cached) return true;

        // 캐시 미스 → DB 조회 후 캐시에 저장
        boolean liked = likeRepository.existsByUserIdAndLikeTargetTypeAndTargetId(userId, targetType, targetId);
        likeCacheRepository.setUserLikedStatus(userId, targetId, targetType, liked);

        return liked;
    }
}