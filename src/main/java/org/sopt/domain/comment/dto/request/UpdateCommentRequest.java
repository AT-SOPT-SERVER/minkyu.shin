package org.sopt.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(
    @NotNull String content
) {
}
