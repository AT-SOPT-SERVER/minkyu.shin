package org.sopt.domain.post.dto;

import lombok.Builder;
import org.sopt.domain.post.domain.Post;

@Builder
public record PostInfoDto(
        long postId,
        String title,
        String authorName
) {
    public static PostInfoDto from(Post post) {
        return PostInfoDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .authorName(post.getUser().getName())
                .build();
    }
}
