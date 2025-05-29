package org.sopt.domain.post.dto;

import lombok.Builder;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostTag;

import java.time.OffsetDateTime;

@Builder
public record PostDto(
        long postId,
        String title,
        String content,
        PostTag tag,
        String authorName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public static PostDto from(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tag(post.getTag())
                .authorName(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
