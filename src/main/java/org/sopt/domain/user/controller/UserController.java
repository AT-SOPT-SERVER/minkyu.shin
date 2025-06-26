package org.sopt.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.constant.ApiResponseMessage;
import org.sopt.domain.user.dto.request.CreateUserRequest;
import org.sopt.domain.user.dto.request.WithdrawRequest;
import org.sopt.domain.user.service.UserService;
import org.sopt.global.annotation.CurrentUserId;
import org.sopt.global.annotation.V1;
import org.sopt.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@V1
@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return ApiResponse.ok(HttpStatus.CREATED, ApiResponseMessage.USER_CREATED_SUCCESS.getMessage());
    }


    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @Valid @RequestBody WithdrawRequest request,
            @CurrentUserId Long userId) {
        userService.withdraw(userId, request.withdrawReason(), request.isAgree());
        return ResponseEntity.noContent().build();
    }
}