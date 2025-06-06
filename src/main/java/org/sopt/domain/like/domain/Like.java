package org.sopt.domain.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private Long targetId; // 게시글 또는 댓글 ID

    @Enumerated(EnumType.STRING)
    private LikeTargetType likeTargetType;

    public static Like create(Long userId, Long targetId, LikeTargetType likeTargetType) {
        return Like.builder()
                .userId(userId)
                .targetId(targetId)
                .likeTargetType(likeTargetType)
                .build();
    }
}