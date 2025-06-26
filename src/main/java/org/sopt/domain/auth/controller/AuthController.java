package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.constant.ApiResponseMessage;
import org.sopt.domain.auth.dto.request.ReissueTokenRequest;
import org.sopt.domain.auth.dto.request.SocialLoginRequest;
import org.sopt.domain.auth.dto.response.TokenResponse;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.service.UserService;
import org.sopt.global.annotation.CurrentUserId;
import org.sopt.global.annotation.ExtractRefreshToken;
import org.sopt.global.annotation.V1;
import org.sopt.global.dto.ApiResponse;
import org.sopt.global.jwt.dto.JwtTokenCollection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@V1
@Tag(name = "인증", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/social/{socialPlaform}/login")
    public ResponseEntity<ApiResponse<TokenResponse>> socialLogin(
            @PathVariable("socialPlaform") SocialPlatform socialPlatform,
            @Valid SocialLoginRequest socialLoginRequest
            ) {
        return ApiResponse.ok(
                HttpStatus.OK,
                ApiResponseMessage.SOCIAL_LOGIN_SUCCESS.getMessage(),
                authService.socialLogin(socialPlatform, socialLoginRequest)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @CurrentUserId Long userId
    ) {
        authService.logout(userId);
        return ApiResponse.ok(HttpStatus.OK, ApiResponseMessage.LOGOUT_SUCCESS.getMessage());
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(
            @ExtractRefreshToken String refreshToken
    ) {
        return ApiResponse.ok(
                HttpStatus.OK,
                ApiResponseMessage.TOKEN_REISSUE_SUCCESS.getMessage(),
                authService.reissue(refreshToken)
        );
    }

}