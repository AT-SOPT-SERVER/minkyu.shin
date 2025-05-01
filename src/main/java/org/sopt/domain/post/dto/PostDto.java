package org.sopt.domain.post.dto;

import org.sopt.domain.post.domain.Post;

import java.time.OffsetDateTime;

public record PostDto(
        Long postId,
        String title,
        String content,
        OffsetDateTime createdAt
) {

    public static PostDto from(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}
