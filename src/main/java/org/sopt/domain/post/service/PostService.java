package org.sopt.domain.post.service;

import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    private static final long TIME_LIMIT = 180;

    public void createPost(String title) {
        Post post = new Post(title);
        postRepository.save(post);
        System.out.println(post.getTitle());
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(final int id) {
        return postRepository.findPostByIdOrThrow(id);
    }

    public void updatePostTitle(final int id, final String newTitle) {
        checkTitleDuplicated(id, newTitle);
        Post post = postRepository.findPostByIdOrThrow(id); // null 체크 제거
        post.changeTitle(newTitle);
    }

    public boolean deletePostById(final int id) {
        return postRepository.delete(id);
    }

    public List<Post> searchPostsByKeyword(final String keyword) {
        List<Post> allPosts = postRepository.findAll();
        List<Post> result = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.getTitle().contains(keyword)) {
                result.add(post);
            }
        }
        return result;
    }

    // 게시글 작성 제한 시간 체크
    public long getPostDelay() {
        Post latestPost = postRepository.findLatestPost();
        if (latestPost == null) return 0;

        LocalDateTime latestPostTime = latestPost.getCreatedAt();
        Duration delay = Duration.between(latestPostTime, LocalDateTime.now());
        return TIME_LIMIT - delay.getSeconds();
    }

    public void checkTitleDuplicated(final String title) {
        for (Post post : postRepository.findAll()) {
            if (post.getTitle().equals(title)) {
                throw new IllegalArgumentException("중복된 제목의 게시물은 작성할 수 없습니다.");
            }
        }
    }

    // 게시글 수정 시 기존의 제목과 같은지 확인하는 로직 추가
    public void checkTitleDuplicated(final int id, final String title) {
        for (Post post : postRepository.findAll()) {
            if (post.getTitle().equals(title) && post.getId() != id) {
                throw new IllegalArgumentException("중복된 제목의 게시물은 작성할 수 없습니다.");
            }
        }
    }



}