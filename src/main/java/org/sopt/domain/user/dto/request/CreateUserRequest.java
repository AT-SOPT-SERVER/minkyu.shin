package org.sopt.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.sopt.domain.user.util.ValidationUtils;

public record CreateUserRequest(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Email must not be blank")
        @Pattern(regexp = ValidationUtils.EMAIL_REGEX, message = "Invalid email format")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Pattern(regexp = ValidationUtils.PASSWORD_REGEX, message = "Invalid password format")
        String password
) {
}
