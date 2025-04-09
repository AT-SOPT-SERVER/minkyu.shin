package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private int postId = 1;

    public boolean createPost(String title) {
        Post post = new Post(postId++, title);
        if (isNotBlank(title)
                || post.isOverMaxTitleLength(title)) return false;
        return postRepository.save(post) != null;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findPostById(id);
    }

    public boolean updatePostTitle(int id, String newTitle) {
        Post post = postRepository.findPostById(id);
        if (post == null
                || isNotBlank(newTitle)
                || post.isOverMaxTitleLength(newTitle)) return false;
        return post.changeTitle(newTitle);
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }

    public boolean isNotBlank(String title) {
        return title != null && !title.isBlank();
    }


}