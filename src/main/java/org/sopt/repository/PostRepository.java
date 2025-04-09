package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostRepository {
    List<Post> postList = new ArrayList<>();

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public List<Post> findAll() {
        return postList;
    }

    public Post findPostById(int id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }

        return null;
    }

    public boolean delete(int id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                postList.remove(post);
                return true;
            }
        }
        return false;
    }

    public Post findLatestPost() {
        return postList.stream()
                .max(Comparator.comparing(Post::getCreatedAt))
                .orElse(null);
    }
}