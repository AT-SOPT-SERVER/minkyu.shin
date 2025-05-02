package org.sopt.domain.post.dto.response;

import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.PostInfoDto;

import java.util.List;

public record GetPostListResponse(
        List<PostInfoDto> postList
) {
    public static GetPostListResponse of(List<PostInfoDto> postList) {
        return new GetPostListResponse(postList);
    }
}
