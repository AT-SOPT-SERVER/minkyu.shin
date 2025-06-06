package org.sopt.domain.like.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByTargetIdAndLikeTargetType(Long targetId, LikeTargetType likeTargetType);

    boolean existsByUserIdAndLikeTargetTypeAndTargetId(Long userId, LikeTargetType targetType, Long targetId);

    @Query("SELECT l.targetId FROM Like l " +
            "WHERE l.userId = :userId " +
            "AND l.likeTargetType = :targetType " +
            "AND l.targetId IN :targetIds")
    List<Long> findLikedTargetIdsByUserId(
            @Param("userId") Long userId,
            @Param("targetType") LikeTargetType targetType,
            @Param("targetIds") List<Long> targetIds
    );
}