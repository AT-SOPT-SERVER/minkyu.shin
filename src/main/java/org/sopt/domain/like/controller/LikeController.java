package org.sopt.domain.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.constant.ApiResponseMessage;
import org.sopt.domain.like.dto.request.LikeToggleRequest;
import org.sopt.domain.like.dto.response.LikeToggleResponse;
import org.sopt.domain.like.service.LikeService;
import org.sopt.global.annotation.V1;
import org.sopt.global.dto.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@V1
@Tag(name = "좋아요", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<CustomApiResponse<LikeToggleResponse>> toggleLike(
//            @CurrentUserId Long userId,
            @Valid @RequestBody LikeToggleRequest likeToggleRequest) {
        Long dummyUserId = 1L;

        return CustomApiResponse.ok(
                HttpStatus.OK,
                ApiResponseMessage.LIKE_STATUS_TOGGLED_SUCCESS.getMessage(),
                LikeToggleResponse.from(likeService.toggleLike(dummyUserId, likeToggleRequest))
        );
    }

}