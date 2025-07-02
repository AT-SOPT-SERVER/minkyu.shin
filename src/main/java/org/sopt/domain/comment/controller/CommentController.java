package org.sopt.domain.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.constant.ApiResponseMessage;
import org.sopt.domain.comment.dto.CommentDto;
import org.sopt.domain.comment.dto.request.CreateCommentRequest;
import org.sopt.domain.comment.dto.request.UpdateCommentRequest;
import org.sopt.domain.comment.service.CommentService;
import org.sopt.global.annotation.CurrentUserId;
import org.sopt.global.annotation.V1;
import org.sopt.global.dto.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@V1
@Tag(name = "댓글", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    ResponseEntity<CustomApiResponse<CommentDto>> createComment(
//            @CurrentUserId Long userId,
            @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        Long dummyUserId = 1L;
        return CustomApiResponse.ok(
                HttpStatus.CREATED,
                ApiResponseMessage.COMMENT_CREATED_SUCCESS.getMessage(),
                commentService.createComment(dummyUserId, createCommentRequest)
        );
    }

    @PatchMapping("/{commentId}")
    ResponseEntity<CustomApiResponse<CommentDto>> updateComment(
            @CurrentUserId Long userId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        return CustomApiResponse.ok(
                HttpStatus.OK,
                ApiResponseMessage.COMMENT_UPDATED_SUCCESS.getMessage(),
                commentService.updateComment(userId, commentId, updateCommentRequest)
        );
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<CustomApiResponse<Void>> deleteComment(
            @CurrentUserId Long userId,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
        return CustomApiResponse.ok(
                HttpStatus.OK,
                ApiResponseMessage.COMMENT_UPDATED_SUCCESS.getMessage()
        );
    }

}