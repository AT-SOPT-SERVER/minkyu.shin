package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
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
import org.sopt.global.dto.PagedResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto createPost(final Long userId, final CreatePostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION));
        validateDuplicatedTitle(request.title());
        validatePostDelay();

        var post = Post.create(
                request.title(),
                request.content(),
                request.tags(),
                user
        );

        return PostDto.from(postRepository.save(post));
    }

    public PagedResponse<PostInfoDto> getAllPosts(final PostSortType sortType, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size, sortType.getSort());
        Page<Post> pagedPosts = postRepository.findAll(pageable);
        List<PostInfoDto> postInfoList = pagedPosts.getContent().stream()
                .map(PostInfoDto::from)
                .toList();

        return new PagedResponse<>(
                postInfoList,
                pagedPosts.getNumber(),
                pagedPosts.getSize(),
                pagedPosts.getTotalElements(),
                pagedPosts.getTotalPages(),
                pagedPosts.isLast()
        );
    }

    public PostDto getPostById(final Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));
        return PostDto.from(post);
    }

    public PagedResponse<PostInfoDto> searchPostsByKeyword(
            final PostSortType sortType,
            final PostSearchType searchType, final String keyword, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size, sortType.getSort());
        Page<Post> pagedPosts = getPagedPosts(keyword, searchType, pageable);
        List<PostInfoDto> postInfoList = pagedPosts.getContent().stream()
                .map(PostInfoDto::from)
                .toList();

        return new PagedResponse<>(
                postInfoList,
                pagedPosts.getNumber(),
                pagedPosts.getSize(),
                pagedPosts.getTotalElements(),
                pagedPosts.getTotalPages(),
                pagedPosts.isLast()
        );
    }

    public List<PostInfoDto> getPostByTag(final PostTag tag) {
        return postRepository.findByTagsContainingOrderByCreatedAtDesc(tag).stream()
                .map(PostInfoDto::from)
                .toList();
    }

    @Transactional
    public PostDto updatePost(final Long userId, final Long id, final UpdatePostRequest request) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));

        if (!post.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_EXCEPTION);
        }

        validateDuplicatedTitle(request.title());
        post.updatePost(request.title(), request.content(), request.postTags());

        return PostDto.from(post);
    }

    @Transactional
    public void deletePostById(final Long userId, final Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_POST_EXCEPTION));

        if (!post.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_EXCEPTION);
        }

        postRepository.deleteById(id);
    }

    private void validateDuplicatedTitle(final String title) {
        if (postRepository.existsPostByTitle(title)) {
            throw new BusinessException(ErrorCode.DUPLICATED_TITLE_EXCEPTION);
        }
    }


    /*
     * 수정 필요!
     */
    private void validatePostDelay() {
        postRepository.findTopByOrderByCreatedAtDesc()
                .ifPresent(lastPost -> {
                    Duration delay = Duration.between(lastPost.getCreatedAt(), OffsetDateTime.now());
                    if (delay.getSeconds() < PostPolicyConstant.POST_DELAY_SECONDS.getValue()) {
                        throw new BusinessException(ErrorCode.POST_DELAY_EXCEPTION);
                    }
                });
    }

    private Page<Post> getPagedPosts(final String keyword, final PostSearchType searchType, final Pageable pageable) {
        return switch (searchType) {
            case TITLE -> postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
            case AUTHOR -> postRepository.findByUserNameContainingIgnoreCase(keyword, pageable);
        };
    }
}