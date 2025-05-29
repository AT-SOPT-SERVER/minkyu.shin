package org.sopt.query;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.service.CommentService;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.dto.response.GetPostDetailsWithCommentsResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryService {

    private final PostService postService;
    private final CommentService commentService;

    public GetPostDetailsWithCommentsResponse getPostWithComments(Long postId) {
        return GetPostDetailsWithCommentsResponse.of(
                postService.getPostById(postId),
                commentService.getAllComments(postId)
        );
    }

}