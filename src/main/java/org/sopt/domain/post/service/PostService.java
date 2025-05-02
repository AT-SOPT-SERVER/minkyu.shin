package org.sopt.domain.post.service;

import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.PostInfoDto;
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
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostDto createPost(final CreatePostRequest request) {
        validateDuplicatedTitle(request.title());
        validatePostDelay();
        var post = Post.createPost(request.title(), request.content());
        postRepository.save(post);
        return PostDto.from(post);
    }

    public List<PostInfoDto> getAllPosts(final PostSortType sortType) {
        if (sortType == PostSortType.LATEST) {
            return postRepository.findAllByOrderByCreatedAtDesc()
                    .stream()
                    .map(PostInfoDto::from)
                    .toList();
        }

        return postRepository.findAll().stream()
                .map(PostInfoDto::from)
                .toList();
    }

    public PostDto getPostById(final Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));
        return PostDto.from(post);
    }

    public List<PostInfoDto> searchPostsByKeyword(final String keyword, final PostSortType sortType) {
        var result = postRepository.findPostsLike(keyword);
        return result.stream().map(PostInfoDto::from).toList();
    }

    @Transactional
    public PostDto updatePost(final Long id, final UpdatePostRequest request) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));

        validateDuplicatedTitle(request.title());
        post.updatePost(request.title(), request.content());

        return PostDto.from(post);
    }

    @Transactional
    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
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