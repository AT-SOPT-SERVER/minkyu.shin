package org.sopt.query;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.dto.CommentDto;
import org.sopt.domain.comment.dto.CommentListDto;
import org.sopt.domain.comment.service.CommentService;
import org.sopt.domain.like.cache.LikeCacheRepository;
import org.sopt.domain.like.domain.LikeTargetType;
import org.sopt.domain.like.repository.LikeRepository;
import org.sopt.domain.like.service.LikeService;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.dto.response.GetPostDetailsWithCommentsResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostQueryService {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    public GetPostDetailsWithCommentsResponse getPostWithComments(Long userId, Long postId) {
        PostDto postDto = postService.getPostById(postId);

        // 게시물 좋아요 정보 조회
        int postLikeCount = likeService.getLikeCount(postId, LikeTargetType.POST);
        boolean userLikedPost = likeService.getUserLikedStatus(userId, postId, LikeTargetType.POST);

        // 댓글 목록 및 좋아요 정보 조회
        CommentListDto commentListDto = commentService.getAllComments(postId);

        List<Long> commentIds = commentListDto.comments().stream().map(CommentDto::commentId).toList();
        Map<Long, Integer> commentLikeCounts = likeService.getLikeCounts(commentIds, LikeTargetType.COMMENT);
        Set<Long> likedCommentIds = likeService.getUserLikedTargetIds(userId, LikeTargetType.COMMENT, commentIds);

        List<CommentDto> commentDtoListWithLike = commentListDto.comments().stream()
                .map(commentDto -> commentDto.withLikeInfo(
                        commentLikeCounts.getOrDefault(commentDto.commentId(), 0),
                        likedCommentIds.contains(commentDto.commentId())
                ))
                .toList();

        return GetPostDetailsWithCommentsResponse.of(
                postDto.withLikeInfo(postLikeCount, userLikedPost),
                CommentListDto.from(commentDtoListWithLike)
        );
    }
}