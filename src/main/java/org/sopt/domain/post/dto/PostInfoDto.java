package org.sopt.domain.post.dto;

import lombok.Builder;
import org.sopt.domain.post.domain.Post;

import java.time.OffsetDateTime;

@Builder
public record PostInfoDto(
        long postId,
        String title,
        String authorName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static PostInfoDto from(Post post) {
        return PostInfoDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .authorName(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
