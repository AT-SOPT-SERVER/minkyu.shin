package org.sopt.domain.post.service;

import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostDto createPost(final CreatePostRequest request) {
        validateDuplicatedTitle(request.title());
        validatePostDelay();
        var post = Post.createPost(request.title());
        postRepository.save(post);
        return PostDto.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostDto::from).toList();
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(final Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));
        return PostDto.from(post);
    }

    @Transactional
    public PostDto updatePostTitle(final Long id, final UpdatePostRequest request) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));

        validateDuplicatedTitle(request.title());
        post.updatePost(request.title());

        return PostDto.from(post);
    }

    @Transactional
    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PostDto> searchPostsByKeyword(final String keyword) {
        var result = postRepository.findAllByTitleContaining(keyword);
        return result.stream().map(PostDto::from).toList();
    }

    private void validateDuplicatedTitle(final String title) {
        if (postRepository.existsPostByTitle(title)) {
            throw new BusinessException(ErrorCode.DUPLICATED_TITLE_EXCEPTION);
        }
    }

    private void validatePostDelay() {
        postRepository.findTopByOrderByCreatedAtDesc()
                .ifPresent(lastPost -> {
                    Duration delay = Duration.between(lastPost.getCreatedAt(), OffsetDateTime.now());
                    if (delay.getSeconds() < PostPolicyConstant.POST_DELAY_SECONDS.getValue()) {
                        throw new BusinessException(ErrorCode.POST_DELAY_EXCEPTION);
                    }
                });
    }

}