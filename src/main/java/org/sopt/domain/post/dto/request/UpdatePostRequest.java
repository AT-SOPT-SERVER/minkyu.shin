package org.sopt.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
