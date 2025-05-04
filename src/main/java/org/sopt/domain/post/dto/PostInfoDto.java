package org.sopt.domain.post.dto;

import org.sopt.domain.post.domain.Post;

public record PostInfoDto(
        long postId,
        String title,
        String authorName
) {
    public static PostInfoDto from(Post post) {
        return new PostInfoDto(
                post.getId(),
                post.getTitle(),
                post.getUser().getName()
        );
    }
}
