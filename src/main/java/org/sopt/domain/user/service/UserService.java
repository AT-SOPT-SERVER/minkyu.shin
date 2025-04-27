package org.sopt.domain.user.service;

import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.dto.UserDto;
import org.sopt.domain.user.dto.request.CreateUserRequest;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(CreateUserRequest createUserRequest) {
        var user = User.create(createUserRequest.name(), createUserRequest.email());
        userRepository.save(user);
        return UserDto.from(user);
    }

}