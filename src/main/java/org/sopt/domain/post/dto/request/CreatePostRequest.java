package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.domain.PostTag;

public record CreatePostRequest(
        String title,
        String content,
        PostTag tag
) {
}
