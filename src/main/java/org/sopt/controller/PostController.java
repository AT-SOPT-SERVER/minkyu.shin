package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    public void createPost(final String title) {
        postService.createPost(title);
    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    public Post getPostById(final int id) {
        return postService.getPostById(id);
    }

    public void updatePostTitle(final int id, final String newTitle) {
        postService.updatePostTitle(id, newTitle);
    }

    public boolean deletePostById(final int id) {
        return postService.deletePostById(id);
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    public long getPostDelay() {
        return postService.getPostDelay();
    }

    public void savePostToFile(String title) throws IOException {
        postService.savePostToFile(title);
    }

    public void loadPostFromFile(String fileName) throws IOException {
        postService.loadPostFromFile(fileName);
    }
}