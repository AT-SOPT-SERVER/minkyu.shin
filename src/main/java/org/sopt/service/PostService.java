package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.PostUtil;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostUtil postUtil = new PostUtil();

    public boolean createPost(String title) {
        if (isInvalidTitle(title)) return false;
        int postId = postUtil.generatePostId();
        Post post = new Post(postId, title);
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
        if (post == null) return false;
        if (isInvalidTitle(newTitle)) return false;
        return post.changeTitle(newTitle);
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }

    // 제목 유효성 검사
    private boolean isInvalidTitle(String title) {
        return isBlank(title)
                || title.length() > 30
                || isTitleDuplicated(title);
    }

    public boolean isBlank(String title) {
        return title == null || title.isBlank();
    }

    public boolean isTitleDuplicated(String title) {
        for (Post post : postRepository.findAll()) {
            if (post.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }




}