package org.sopt.domain.post.domain;

import jakarta.persistence.*;
import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.domain.post.util.TextLengthUtil;
import org.sopt.domain.user.domain.User;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Table(
        indexes = {
                @Index(name = "post_title_idx", columnList = "title", unique = true),
                @Index(name = "post_user_id_idx", columnList = "user_id")
        }
)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Post() {}

    public Post(String title, String content) {
        validate(title, content);
        this.title = title;
        this.content = content;
    }

    public static Post createPost(String title, String content) {
        return new Post(title, content);
    }

    public void updatePost(String newTitle, String newContent) {
        validate(newTitle, newContent);
        this.title = newTitle;
        this.content = newContent;
    }

    private void validate(String title, String content) {
        validateBlank(title);
        validateTitleLength(title);

        validateBlank(content);
        validateContentLength(content);
    }

    private void validateBlank(String text) {
        if (text == null || text.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }

    private void validateTitleLength(String text) {
        if (TextLengthUtil.visibleLength(text) > PostPolicyConstant.TITLE_MAX_LENGTH.getValue()) {
            throw new BusinessException(ErrorCode.INVALID_TITLE_LENGTH_EXCEPTION);
        }
    }

    private void validateContentLength(String text) {
        if (TextLengthUtil.visibleLength(text) > PostPolicyConstant.CONTENT_MAX_LENGTH.getValue()) {
            throw new BusinessException(ErrorCode.INVALID_CONTENT_LENGTH_EXCEPTION);
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public User getUser() {
        return this.user;
    }

}