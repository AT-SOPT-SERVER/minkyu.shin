package org.sopt.domain.post.repository;

import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsPostByTitle(String title);

    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Post> findByUserNameContainingIgnoreCase(String keyword, Pageable pageable);

    Optional<Post> findTopByOrderByCreatedAtDesc();

    List<Post> findByTagsContainingOrderByCreatedAtDesc(PostTag tag);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = :count WHERE p.id = :postId")
    void updateLikeCount(@Param("postId") Long postId, @Param("count") int count);
}