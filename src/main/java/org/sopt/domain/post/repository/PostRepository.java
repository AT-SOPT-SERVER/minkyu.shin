package org.sopt.domain.post.repository;

import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsPostByTitle(String title);

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByTitleContainingOrderByCreatedAtDesc(String keyword);

    List<Post> findByUserNameContainingOrderByCreatedAtDesc(String keyword);

    Optional<Post> findTopByOrderByCreatedAtDesc();

    List<Post> findByTagOrderByCreatedAtDesc(PostTag tag);
}