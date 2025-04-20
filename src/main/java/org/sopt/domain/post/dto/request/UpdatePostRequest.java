package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.util.PostRequestValidation;

public record UpdatePostRequest(
        String title
) {
    public void validate() {
        PostRequestValidation.validateInput(title);
    }
}
