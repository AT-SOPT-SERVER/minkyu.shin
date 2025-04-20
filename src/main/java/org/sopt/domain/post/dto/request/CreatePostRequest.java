package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.util.PostRequestValidation;

public record CreatePostRequest(
        String title
) {
    public void validate() {
        PostRequestValidation.validateInput(title);
    }
}
