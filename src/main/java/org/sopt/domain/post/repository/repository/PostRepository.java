package org.sopt.domain.post.repository.repository;

import org.sopt.domain.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class PostRepository {
    List<Post> postList = new ArrayList<>();

    private static final String FILE_PATH = "asset/";

    public void save(Post post) {
        postList.add(post);
    }

    public List<Post> findAll() {
        return postList;
    }

    public Post findPostByIdOrThrow(int id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        throw new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.");
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