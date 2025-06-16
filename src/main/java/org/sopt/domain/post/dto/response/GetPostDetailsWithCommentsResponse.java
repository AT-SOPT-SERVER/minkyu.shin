package org.sopt.domain.post.dto.response;

import org.sopt.domain.comment.dto.CommentDto;
import org.sopt.domain.comment.dto.CommentListDto;
import org.sopt.domain.post.dto.PostDto;

import java.util.List;

public record GetPostDetailsWithCommentsResponse(
        PostDto post,
        List<CommentDto> commentList
) {
    public static GetPostDetailsWithCommentsResponse of(PostDto postDto, List<CommentDto> commentDtoList) {
        return new GetPostDetailsWithCommentsResponse(postDto, commentDtoList);
    }
}
