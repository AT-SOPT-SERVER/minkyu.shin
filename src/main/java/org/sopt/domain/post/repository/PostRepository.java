package org.sopt.domain.post.repository;

import org.sopt.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p"
            + " ORDER BY p.createdAt DESC "
            + " LIMIT 1")
    Optional<Post> findLatestPost();

    boolean existsPostByTitle(String title);

}