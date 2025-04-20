package org.sopt.domain.post.service;

import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
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

    public PostDto createPost(final CreatePostRequest request) {

        var post = Post.createPost(request.title());
        postRepository.save(post);
        return PostDto.from(post);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostDto::from).toList();
    }

    public PostDto getPostById(final Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));
        return PostDto.from(post);
    }

    public PostDto updatePostTitle(final Long id, final UpdatePostRequest request) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));
        post.changeTitle(request.title());
        return PostDto.from(post);
    }

    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
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