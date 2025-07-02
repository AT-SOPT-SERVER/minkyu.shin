package org.sopt.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.domain.post.domain.PostTag;

import java.util.Set;

public record UpdatePostRequest(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        Set<PostTag> postTags
) {
}
