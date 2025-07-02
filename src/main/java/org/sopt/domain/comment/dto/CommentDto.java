package org.sopt.domain.comment.dto;

import lombok.Builder;
import org.sopt.domain.comment.domain.Comment;

import java.time.OffsetDateTime;

@Builder
public record CommentDto(
        Long commentId,
        String content,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long userId,
        String name,
        int likeCount,
        boolean likedByCurrentUser
) {
    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .userId(comment.getUser().getId())
                .name(comment.getUser().getName())
                .build();
    }

    public CommentDto withLikeInfo(int likeCount, boolean likedByCurrentUser) {
        return CommentDto.builder()
                .commentId(this.commentId)
                .content(this.content)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .userId(this.userId)
                .name(this.name)
                .likeCount(likeCount)
                .likedByCurrentUser(likedByCurrentUser)
                .build();
    }
}
