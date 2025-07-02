package org.sopt.domain.user.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserRole;
import org.sopt.domain.user.dto.request.CreateUserRequest;
import org.sopt.domain.user.repository.SocialUserInfoRepository;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SocialUserInfoRepository socialUserInfoRepository;

    public void createUser(CreateUserRequest createUserRequest) {
        // 이메일을 기준으로 중복된 유저 검사
        Optional<User> user = userRepository.findByEmail(createUserRequest.email());

        if (user.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_EXCEPTION);
        }

        var newUser = User.create(
                createUserRequest.name(),
                createUserRequest.email(),
                createUserRequest.password(),
                UserRole.MEMBER
        );

        userRepository.save(newUser);
    }

    public void withdraw(final Long userId, String withdrawReason, @NotNull boolean isAgree) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        socialUserInfoRepository.deleteByUserId(userId);
        user.withdraw();

    }
}