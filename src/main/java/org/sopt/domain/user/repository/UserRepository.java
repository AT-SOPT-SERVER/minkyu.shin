package org.sopt.domain.user.repository;

import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.status = :status")
    Optional<User> findByIdAndStatus(@Param("id") Long id, @Param("status") UserStatus status);

    User findByEmailAndStatus(String email, UserStatus status);
}