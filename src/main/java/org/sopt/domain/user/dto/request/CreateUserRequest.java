package org.sopt.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.sopt.domain.user.util.ValidationUtils;

public record CreateUserRequest(
        @NotEmpty(message = "Name must not be empty")
        String name,

        @NotEmpty(message = "Email must not be empty")
        @Pattern(regexp = ValidationUtils.EMAIL_REGEX, message = "Invalid email format")
        String email,

        @NotEmpty(message = "Password must not be empty")
        @Pattern(regexp = ValidationUtils.PASSWORD_REGEX, message = "Invalid password format")
        String password
) {
}
