package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.util.PostRequestValidator;

public record CreatePostRequest(
        String title
) {

    public void validate(PostRequestValidator postRequestValidator) {
        postRequestValidator.validateTitle(title);
    }

}
