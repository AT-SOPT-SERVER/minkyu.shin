package org.sopt.domain.user.dto;

import org.sopt.domain.user.domain.User;

public record UserDto(
        Long userId,
        String name,
        String email
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
