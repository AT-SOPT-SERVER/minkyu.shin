package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.util.PostRequestValidator;

public record UpdatePostRequest(
        String title
) {
}
