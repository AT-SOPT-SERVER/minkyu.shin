package org.sopt.domain.comment.dto;

import java.util.List;

public record CommentListDto(
        List<CommentDto> commentList
) {
    public static CommentListDto from(List<CommentDto> commentList) {
        return new CommentListDto(commentList);
    }
}
