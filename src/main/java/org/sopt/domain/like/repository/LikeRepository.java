package org.sopt.domain.like.repository;

import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Integer countByTargetIdAndLikeTargetType(Long targetId, LikeTargetType likeTargetType);

    boolean existsByUserIdAndLikeTargetTypeAndTargetId(Long userId, LikeTargetType targetType, Long targetId);
}