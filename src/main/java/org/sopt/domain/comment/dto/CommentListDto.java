package org.sopt.domain.comment.dto;

import java.util.List;

public record CommentListDto(
        List<CommentDto> comments
) {
    public static CommentListDto from(List<CommentDto> comments) {
        return new CommentListDto(comments);
    }
}
