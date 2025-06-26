package org.sopt.domain.user.repository;

import org.sopt.domain.user.domain.SocialPlatform;
import org.sopt.domain.user.domain.SocialUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocialUserInfoRepository extends JpaRepository<SocialUserInfo, Long> {

    Optional<SocialUserInfo> findBySocialCode(String socialCode);

    @Query("SELECT s FROM SocialUserInfo s " +
            "JOIN FETCH s.user " +
            "WHERE s.socialCode = :socialCode")
    Optional<SocialUserInfo> findBySocialCodeWithUser(@Param("socialCode") String socialCode);

    boolean existsBySocialCode(String socialCode);

    @Query("SELECT COUNT(s) FROM SocialUserInfo s WHERE s.user.id = :userId")
    int countByUserId(@Param("userId") Long userId);

    Optional<SocialUserInfo> findByUserIdAndSocialPlatform(Long userId, SocialPlatform platform);

    void deleteByUserId(Long userId);
}