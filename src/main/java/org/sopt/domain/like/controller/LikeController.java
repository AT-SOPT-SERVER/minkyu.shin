package org.sopt.domain.like.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.like.dto.request.LikeToggleRequest;
import org.sopt.domain.like.dto.response.LikeToggleResponse;
import org.sopt.domain.like.service.LikeService;
import org.sopt.global.annotation.CurrentUserId;
import org.sopt.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse<LikeToggleResponse>> toggleLike(
//            @CurrentUserId Long userId,
            @Valid @RequestBody LikeToggleRequest likeToggleRequest) {
        Long dummyUserId = 1L;

        return ResponseEntity.ok(ApiResponse.ok(
                new LikeToggleResponse(
                        likeService.toggleLike(dummyUserId, likeToggleRequest))
                )
        );
    }

}