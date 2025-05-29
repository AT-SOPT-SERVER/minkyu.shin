package org.sopt.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.dto.CommentDto;
import org.sopt.domain.comment.dto.request.CreateCommentRequest;
import org.sopt.domain.comment.dto.request.UpdateCommentRequest;
import org.sopt.domain.comment.service.CommentService;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.global.annotation.CurrentUserId;
import org.sopt.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    ResponseEntity<ApiResponse<CommentDto>> createComment(
            @CurrentUserId Long userId,
            @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(
                ApiResponse.ok(commentService.createComment(userId, createCommentRequest))
        );
    }

    @PatchMapping("/{commentId}")
    ResponseEntity<ApiResponse<CommentDto>> updateComment(
            @CurrentUserId Long userId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        return ResponseEntity.ok(
                ApiResponse.ok(commentService.updateComment(userId, commentId, updateCommentRequest))
        );
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<ApiResponse<Void>> deleteComment(
            @CurrentUserId Long userId,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}