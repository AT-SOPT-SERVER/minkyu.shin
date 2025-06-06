package org.sopt.domain.post.dto.response;

import org.sopt.domain.comment.dto.CommentListDto;
import org.sopt.domain.post.dto.PostDto;

public record GetPostDetailsWithCommentsResponse(
        PostDto post,
        CommentListDto commentList
) {
    public static GetPostDetailsWithCommentsResponse of(PostDto postDto, CommentListDto commentListDto) {
        return new GetPostDetailsWithCommentsResponse(postDto, commentListDto);
    }
}
