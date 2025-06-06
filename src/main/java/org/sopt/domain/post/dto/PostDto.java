package org.sopt.domain.post.dto;

import lombok.Builder;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostTag;

import java.time.OffsetDateTime;

@Builder
public record PostDto(
        Long postId,
        String title,
        String content,
        PostTag tag,
        String authorName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        int likeCount,
        boolean likedByCurrentUser
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

    public PostDto withLikeInfo(int likeCount, boolean likedByCurrentUser) {
        return PostDto.builder()
                .postId(this.postId)
                .title(this.title)
                .content(this.content)
                .tag(this.tag)
                .authorName(this.authorName)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .likeCount(likeCount)
                .likedByCurrentUser(likedByCurrentUser)
                .build();
    }

}
