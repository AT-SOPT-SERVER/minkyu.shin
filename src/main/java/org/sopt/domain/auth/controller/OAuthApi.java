package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.domain.auth.dto.request.SocialLoginRequest;
import org.sopt.domain.auth.dto.response.TokenResponse;
import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.global.dto.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "인증 관련 API")
@RequestMapping("/api/auth")
public interface OAuthApi {

    @Operation(summary = "소셜 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/social/{socialPlatform}/login")
    ResponseEntity<CustomApiResponse<TokenResponse>> socialLogin(
            @Parameter(description = "소셜 플랫폼 종류 (e.g., KAKAO, GOOGLE)", required = true)
            @PathVariable("socialPlatform") SocialPlatform socialPlatform,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "소셜 로그인 요청 본문",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SocialLoginRequest.class))
            )
            @RequestBody SocialLoginRequest socialLoginRequest
    );

    @Operation(summary = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/logout")
    ResponseEntity<CustomApiResponse<Void>> logout(
            @Parameter(description = "현재 로그인된 사용자 ID", required = true)
            @RequestHeader("X-USER-ID") Long userId // @CurrentUserId 대체
    );

    @Operation(summary = "토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh 토큰 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/reissue")
    ResponseEntity<CustomApiResponse<TokenResponse>> reissue(
            @Parameter(description = "헤더에 담긴 Refresh Token", required = true)
            @RequestHeader("X-REFRESH-TOKEN") String refreshToken // @ExtractRefreshToken 대체
    );
}