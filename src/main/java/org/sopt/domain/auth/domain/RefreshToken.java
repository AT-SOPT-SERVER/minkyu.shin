package org.sopt.domain.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.user.domain.User;
import org.sopt.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String token;

    public RefreshToken(User user, String token) {
        this.user = user;
        this.token = token;
    }


    public static RefreshToken create(User user, String token) {
        return new RefreshToken(
                user,
                token
        );
    }
}