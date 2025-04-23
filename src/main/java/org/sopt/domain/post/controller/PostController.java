package org.sopt.domain.post.controller;

import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.dto.response.GetPostListResponse;
import org.sopt.domain.post.service.PostService;
import org.sopt.domain.post.util.PostRequestValidator;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(
            PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody final CreatePostRequest createPostRequest) {
        PostRequestValidator.validateInput(createPostRequest.title());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(createPostRequest));
    }

    @GetMapping
    public ResponseEntity<GetPostListResponse> getAllPosts() {
        return ResponseEntity.ok(GetPostListResponse.of(postService.getAllPosts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable final Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<GetPostListResponse> searchPostsByKeyword(
            @RequestParam String keyword) {
        return ResponseEntity.ok(GetPostListResponse.of(postService.searchPostsByKeyword(keyword)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePostTitle(
            @PathVariable final Long id,
            @RequestBody final UpdatePostRequest updatePostRequest) {
        PostRequestValidator.validateInput(updatePostRequest.title());
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.updatePostTitle(id, updatePostRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable final Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

}