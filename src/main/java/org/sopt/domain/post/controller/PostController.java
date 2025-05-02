package org.sopt.domain.post.controller;

import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.dto.response.GetPostListResponse;
import org.sopt.domain.post.service.PostService;
import org.sopt.domain.post.util.PostRequestValidator;
import org.sopt.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(
            @RequestBody final CreatePostRequest createPostRequest) {
        PostRequestValidator.validateNull(createPostRequest.title());
        PostRequestValidator.validateNull(createPostRequest.content());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(postService.createPost(createPostRequest)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<GetPostListResponse>> getPosts(
            @RequestParam(required = false, name = "sortBy", defaultValue = "LATEST") PostSortType sortType,
            @RequestParam(required = false, name = "keyword") String keyword) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                    GetPostListResponse.of(
                    (keyword == null || keyword.trim().isEmpty()) ?
                            postService.getAllPosts(sortType)
                            : postService.searchPostsByKeyword(keyword, sortType)
                    )
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPostById(@PathVariable final Long id) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        postService.getPostById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> updatePostTitle(
            @PathVariable final Long id,
            @RequestBody final UpdatePostRequest updatePostRequest) {
        PostRequestValidator.validateNull(updatePostRequest.title());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(postService.updatePost(id, updatePostRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable final Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}