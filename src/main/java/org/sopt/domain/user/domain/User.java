package org.sopt.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.ErrorCode;

import javax.swing.*;

import static org.sopt.domain.user.util.ValidationUtils.validateEmailFormat;
import static org.sopt.domain.user.util.ValidationUtils.validatePasswordFormat;
import static org.sopt.global.util.InputValidator.validateLength;
import static org.sopt.global.util.InputValidator.validateNullOrBlank;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length =10)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    @ColumnDefault(value = "'MEMBER'")
    private UserRole userRole;


    public static User create(String name, String email, String password, UserRole userRole) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);

        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    private static void validatePassword(String password) {
        validateNullOrBlank(password, ErrorCode.PASSWORD_NULL_OR_BLANK_EXCEPTION);
        validatePasswordFormat(password, ErrorCode.INVALID_PASSWORD_FORMAT_EXCEPTION);
    }

    public static void validateName(String name) {
        validateNullOrBlank(name, ErrorCode.NAME_NULL_OR_BLANK_EXCEPTION);
        validateLength(name, 0, 10, ErrorCode.INVALID_NAME_LENGTH_EXCEPTION);
    }

    private static void validateEmail(String email) {
        validateNullOrBlank(email, ErrorCode.EMAIL_NULL_OR_BLANK_EXCEPTION);
        validateEmailFormat(email, ErrorCode.INVALID_EMAIL_FORMAT_EXCEPTION);
    }
}