package org.sopt.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.domain.comment.constant.CommentPolicyConstant;

public record CreateCommentRequest(
        @NotBlank String content,
        @NotNull Long postId
) {
}
