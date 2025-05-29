package org.sopt.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.domain.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length =10)
    private String name;

    @Column(nullable = false)
    private String email;


    public static User create(String name, String email) {
        validate(name, email);
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    public static void validate(String name, String email) {
        validateName(name);
        validateEmail(email);
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("낙네임은 null 값이거나 비어있을 수 없습니다");
        }
        if (name.length() > 10) {
            throw new IllegalArgumentException("낙네임은 10자 이하이어야 합니다");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 null 값이거나 비어있을 수 없습니다");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다");
        }
    }
}