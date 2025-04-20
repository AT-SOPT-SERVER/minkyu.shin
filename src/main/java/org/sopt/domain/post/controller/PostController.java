package org.sopt.domain.post.controller;

import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.dto.PostRequest;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/post")
    public void createPost(@RequestBody final PostRequest postRequest) {
        postService.createPost(postRequest.getTitle());
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    public Post getPostById(final int id) {
        return postService.getPostById(id);
    }

    public void updatePostTitle(final int id, final String newTitle) {
        postService.updatePostTitle(id, newTitle);
    }

    public boolean deletePostById(final int id) {
        return postService.deletePostById(id);
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    public long getPostDelay() {
        return postService.getPostDelay();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getMessage()));
    }

}