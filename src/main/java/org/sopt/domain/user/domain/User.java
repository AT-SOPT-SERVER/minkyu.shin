package org.sopt.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.sopt.domain.user.util.ValidationUtils.validateEmailFormat;
import static org.sopt.domain.user.util.ValidationUtils.validatePasswordFormat;
import static org.sopt.global.util.InputValidator.validateLength;
import static org.sopt.global.util.InputValidator.validateNullOrBlank;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_created_at", columnList = "createdAt")
})
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(length = 20)
    private String password;  // 소셜 로그인 시 null 가능

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault(value = "'MEMBER'")
    private UserRole userRole;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    @ColumnDefault(value = "'ACTIVE'")
    private UserStatus status;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted = false;


    // 일반 회원가입용 정적 팩토리 메서드
    public static User create(String name, String email, String password, UserRole userRole) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);

        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .userRole(userRole)
                .status(UserStatus.ACTIVE)
                .build();
    }

    // 소셜 로그인용 정적 팩토리 메서드
    public static User createSocialUser(String name, String email, UserRole userRole) {
        validateName(name);
        validateEmail(email);

        return User.builder()
                .name(name)
                .email(email)
                .userRole(userRole)
                .status(UserStatus.ACTIVE)
                .build();
    }

    // 검증 메서드
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

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void withdraw() {
        this.status = UserStatus.WITHDRAWN;
        this.name = "탈퇴한 회원";
        this.email = null;
        this.password = null;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}