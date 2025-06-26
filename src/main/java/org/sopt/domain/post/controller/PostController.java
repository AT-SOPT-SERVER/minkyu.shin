package org.sopt.domain.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.constant.PostSearchType;
import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.domain.PostTag;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.PostInfoDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.dto.response.GetPostListResponse;
import org.sopt.domain.post.service.PostService;
import org.sopt.global.annotation.V1;
import org.sopt.global.dto.ApiResponse;
import org.sopt.domain.post.dto.response.GetPostDetailsWithCommentsResponse;
import org.sopt.global.dto.PagedResponse;
import org.sopt.query.PostQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sopt.domain.post.constant.ApiResponseMessage.*;

@V1
@Tag(name = "게시글", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostQueryService postQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(
            @RequestHeader("X-USER-ID") final Long userId,
            @Valid @RequestBody final CreatePostRequest createPostRequest) {
        return ApiResponse.ok(
                HttpStatus.CREATED, POST_CREATED_SUCCESS.getMessage(), postService.createPost(userId, createPostRequest)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<PostInfoDto>>> getPosts (
            @RequestParam(required = false, defaultValue = "LATEST", name = "sortBy") PostSortType sortType,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, name = "search-type") PostSearchType searchType,
            @RequestParam(required = false, name = "keyword") String keyword) {
        PagedResponse<PostInfoDto> pagedResponse = (keyword == null || keyword.trim().isEmpty()) ?
                postService.getAllPosts(sortType, page, size)
                : postService.searchPostsByKeyword(sortType, searchType, keyword, page, size);
        return ApiResponse.ok(
                HttpStatus.OK,
                POST_GET_SUCCESS.getMessage(),
                pagedResponse
        );
    }

    @GetMapping("/tags/{tag}")
    public ResponseEntity<ApiResponse<GetPostListResponse>> getPostByTag(
            @PathVariable final PostTag tag) {
        return ApiResponse.ok(
                HttpStatus.OK,
                POST_GET_SUCCESS.getMessage(),
                GetPostListResponse.of(
                        postService.getPostByTag(tag)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetPostDetailsWithCommentsResponse>> getPostById(
//            @CurrentUserId Long userId,
            @PathVariable final Long id) {
        Long dummyUserId = 1L;
        return ApiResponse.ok(
                HttpStatus.OK,
                POST_DETAILS_GET_SUCCESS.getMessage(),
                postQueryService.getPostWithComments(dummyUserId, id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> updatePost(
            @RequestHeader("X-USER-ID") final Long userId,
            @PathVariable final Long id,
            @Valid @RequestBody final UpdatePostRequest updatePostRequest) {
        return ApiResponse.ok(
                HttpStatus.OK,
                POST_DETAILS_GET_SUCCESS.getMessage(),
                postService.updatePost(userId, id, updatePostRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestHeader("X-USER-ID") final Long userId,
            @PathVariable final Long id) {
        postService.deletePostById(userId, id);
        return ApiResponse.ok(HttpStatus.OK, POST_DELETED_SUCCESS.getMessage());
    }

}