package org.sopt.domain.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.like.cache.LikeCacheConstants;
import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeTargetType;
import org.sopt.domain.like.repository.LikeRepository;
import org.sopt.global.cache.RedisCacheRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.sopt.domain.like.cache.LikeCacheConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeStatusFlushService {

    private final RedisCacheRepository redisCacheRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void flushUserLikeStatus() {
        Set<String> keys = redisCacheRepository.findKeys(LIKE_STATUS_KEY_PATTERN);

        for (String key : keys) {
            LikeTargetType type = extractType(key);         // likeStatus:post:1:user:2 → POST
            Long targetId = extractTargetId(key);           // → 1
            Long userId = extractUserId(key);               // → 2

            boolean liked = Boolean.parseBoolean(redisCacheRepository.get(key, String.class));
            syncUserLikeStatus(userId, targetId, type, liked);

            log.info("[{}] 좋아요 상태 Flush - userId: {}, targetId: {}, 상태: {}",
                    type.name(), userId, targetId, liked ? "좋아요" : "취소");
        }
    }

    public void syncUserLikeStatus(Long userId, Long targetId, LikeTargetType type, boolean liked) {
        boolean exists = likeRepository.existsByUserIdAndLikeTargetTypeAndTargetId(userId, type, targetId);

        if (liked && !exists) {
            likeRepository.save(Like.create(userId, targetId, type));
        } else if (!liked && exists) {
            likeRepository.deleteByUserIdAndTargetIdAndLikeTargetType(userId, targetId, type);
        }
    }

    private LikeTargetType extractType(String key) {
        String[] parts = key.split(":");
        return LikeTargetType.valueOf(parts[1].toUpperCase());
    }

    private Long extractTargetId(String key) {
        String[] parts = key.split(":");
        try {
            return Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.INVALID_CACHE_KEY_FORMAT);
        }
    }

    private Long extractUserId(String key) {
        String[] parts = key.split(":");
        try {
            return Long.parseLong(parts[4]);
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.INVALID_CACHE_KEY_FORMAT);
        }
    }
}