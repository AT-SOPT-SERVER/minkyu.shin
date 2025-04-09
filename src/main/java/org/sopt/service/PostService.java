package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.PostUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostUtil postUtil = new PostUtil();
    private static final long TIME_LIMIT = 180;

    public void createPost(String title) {
        checkTitleDuplicated(title);
        int postId = postUtil.generatePostId();
        Post post = new Post(postId, title);
        postRepository.save(post);
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
        checkTitleDuplicated(id, newTitle);
        return post.changeTitle(newTitle);
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }

    // 게시글 작성 제한 시간 체크
    public long getPostDelay() {
        Post latestPost = postRepository.findLatestPost();
        if (latestPost == null) return 0;

        LocalDateTime latestPostTime = latestPost.getCreatedAt();
        Duration delay = Duration.between(latestPostTime, LocalDateTime.now());
        return TIME_LIMIT - delay.getSeconds();
    }

    public void checkTitleDuplicated(String title) {
        for (Post post : postRepository.findAll()) {
            if (post.getTitle().equals(title)) {
                throw new IllegalArgumentException("중복된 제목의 게시물은 작성할 수 없습니다.");
            }
        }
    }

    // 게시글 수정 시 기존의 제목과 같은지 확인하는 로직 추가
    public void checkTitleDuplicated(int id, String title) {
        for (Post post : postRepository.findAll()) {
            if (post.getId() == id) {
                throw new IllegalArgumentException("기존 제목과 동일합니다.");
            }
            if (post.getTitle().equals(title)) {
                throw new IllegalArgumentException("중복된 제목의 게시물은 작성할 수 없습니다.");
            }
        }
    }
}