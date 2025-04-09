package org.sopt.repository;

import org.sopt.domain.Post;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostRepository {
    List<Post> postList = new ArrayList<>();

    private static final String FILE_PATH = "asset/";

    public void save(Post post) {
        postList.add(post);
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

    public void savePostToFile(Post post) throws IOException {
        String filePath = FILE_PATH + post.getTitle() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write( post.getTitle());
        } catch (IOException e) {
            throw new IOException("파일 저장에 실패했습니다.", e);
        }
    }
}