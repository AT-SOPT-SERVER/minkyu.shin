package org.sopt.domain.auth.repository;

import org.sopt.domain.auth.domain.RefreshToken;
import org.sopt.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}