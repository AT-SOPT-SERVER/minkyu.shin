package org.sopt.domain.post.dto.response;

import org.sopt.domain.post.dto.PostDto;

import java.time.OffsetDateTime;

public record GetPostDetailsResponse(
        Long postId,
        String title,
        String content,
        String authorName,
        OffsetDateTime createdAt
) {
    public static GetPostDetailsResponse of(PostDto postDto) {
        return new GetPostDetailsResponse(
                postDto.postId(),
                postDto.title(),
                postDto.content(),
                postDto.authorName(),
                postDto.createdAt()
        );
    }
}
