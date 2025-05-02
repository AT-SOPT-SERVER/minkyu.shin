package org.sopt.domain.post.controller;

import org.sopt.domain.post.constant.PostSearchType;
import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.domain.PostTag;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.dto.response.GetPostListResponse;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.dto.ApiResponse;
import org.sopt.global.util.InputValidator;
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
            @RequestHeader("X-USER-ID") final Long userId,
            @RequestBody final CreatePostRequest createPostRequest) {
        InputValidator.validateNullOrBlank(createPostRequest.title());
        InputValidator.validateNullOrBlank(createPostRequest.content());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(postService.createPost(userId, createPostRequest)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<GetPostListResponse>> getPosts(
            @RequestParam(required = false, name = "sortBy") PostSortType sortType,
            @RequestParam(required = false, name = "search-type") PostSearchType searchType,
            @RequestParam(required = false, name = "keyword") String keyword) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                    GetPostListResponse.of(
                    (keyword == null || keyword.trim().isEmpty()) ?
                            postService.getAllPosts(sortType)
                            : postService.searchPostsByKeyword(searchType, keyword)
                    )
                )
        );
    }

    @GetMapping("/tags/{tag}")
    public ResponseEntity<ApiResponse<GetPostListResponse>> getPostByTag(
            @PathVariable final PostTag tag) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        GetPostListResponse.of(
                                postService.getPostByTag(tag)
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
        InputValidator.validateNullOrBlank(updatePostRequest.title());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(postService.updatePost(id, updatePostRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@PathVariable final Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}