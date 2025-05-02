package org.sopt.domain.post.service;

import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.domain.post.constant.PostSearchType;
import org.sopt.domain.post.constant.PostSortType;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostTag;
import org.sopt.domain.post.dto.PostDto;
import org.sopt.domain.post.dto.PostInfoDto;
import org.sopt.domain.post.dto.request.CreatePostRequest;
import org.sopt.domain.post.dto.request.UpdatePostRequest;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostDto createPost(final Long userId, final CreatePostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION));
        validateDuplicatedTitle(request.title());
        validatePostDelay();

        var post = Post.createPost(
                request.title(),
                request.content(),
                request.tag(),
                user
        );

        return PostDto.from(postRepository.save(post));
    }

    public List<PostInfoDto> getAllPosts(final PostSortType sortType) {
        if (sortType == PostSortType.TIME) {
            List<Post> result = postRepository.findAllByOrderByCreatedAtDesc();
            return result.stream().map(PostInfoDto::from).toList();
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

    public List<PostInfoDto> searchPostsByKeyword(
            final PostSearchType searchType, final String keyword) {
        List<Post> posts = new ArrayList<>();
        if (searchType == PostSearchType.TITLE) {
            posts = postRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);
        } else if (searchType == PostSearchType.AUTHOR) {
            posts = postRepository.findByUserNameContainingOrderByCreatedAtDesc(keyword);
        }

        return posts.stream().map(PostInfoDto::from).toList();
    }

    public List<PostInfoDto> getPostByTag(final PostTag tag) {
        return postRepository.findByTagOrderByCreatedAtDesc(tag).stream()
                .map(PostInfoDto::from)
                .toList();
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