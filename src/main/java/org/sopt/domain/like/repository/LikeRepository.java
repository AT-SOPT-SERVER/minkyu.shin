package org.sopt.domain.like.repository;

import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndLikeTargetAndTargetId(Long userId, LikeTargetType likeTargetType, Long targetId);

    Integer countByTargetIdAndLikeTarget(Long postId, LikeTargetType likeTargetType);
}