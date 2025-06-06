package org.sopt.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.comment.constant.CommentPolicyConstant;
import org.sopt.domain.comment.domain.Comment;
import org.sopt.domain.comment.dto.CommentDto;
import org.sopt.domain.comment.dto.CommentListDto;
import org.sopt.domain.comment.dto.request.CreateCommentRequest;
import org.sopt.domain.comment.dto.request.UpdateCommentRequest;
import org.sopt.domain.comment.repository.CommentRepository;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto createComment(final Long userId, final CreateCommentRequest createCommentRequest) {
        Comment comment = Comment.create(
                postRepository.findById(createCommentRequest.postId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION)),
                userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION)),
                createCommentRequest.content()
        );

        return CommentDto.from(commentRepository.save(comment));
    }

    public CommentListDto getAllComments(final Long postId) {
        return CommentListDto.from(
                commentRepository.findAllByPostId(postId).stream()
                        .map(CommentDto::from)
                        .toList()
        );
    }

    @Transactional
    public CommentDto updateComment(
            final Long commentId, final Long userId, final UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION));

        checkAccessPermission(userId, comment);
        comment.updateContent(updateCommentRequest.content());

        return CommentDto.from(comment);
    }

    @Transactional
    public void deleteComment(final Long userId, final Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE_EXCEPTION));

        checkAccessPermission(userId, comment);
        commentRepository.delete(comment);
    }

    private static void checkAccessPermission(final Long userId, final Comment comment) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_EXCEPTION);
        }
    }

}