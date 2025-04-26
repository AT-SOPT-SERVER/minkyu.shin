package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.util.PostRequestValidator;

public record CreatePostRequest(
        String title
) {
}
