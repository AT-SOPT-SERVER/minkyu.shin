package org.sopt.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.dto.UserDto;
import org.sopt.domain.user.dto.request.CreateUserRequest;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(CreateUserRequest createUserRequest) {
        var user = User.create(createUserRequest.name(), createUserRequest.email());
        return UserDto.from(userRepository.save(user));
    }

}