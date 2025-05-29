package org.sopt.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotBlank
        @Size(min = 1, max = 30)
        String title,

        @NotBlank
        @Size(min = 1, max = 1000)
        String content
) {
}
