package org.sopt.domain.post.dto.response;

import org.sopt.domain.post.dto.PostDto;

import java.util.List;

public record GetPostListResponse(
        List<PostDto> postList
) {
    public static GetPostListResponse of(List<PostDto> postList) {
        return new GetPostListResponse(postList);
    }
}
