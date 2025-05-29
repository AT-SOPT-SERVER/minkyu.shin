package org.sopt.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.global.util.TextLengthUtil;
import org.sopt.domain.user.domain.User;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "post_title_idx", columnList = "title", unique = true),
                @Index(name = "post_user_id_idx", columnList = "user_id"),
                @Index(name = "post_tag_idx", columnList = "tag")
        }
)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private PostTag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Post create(String title, String content, PostTag tag, User user) {
        validate(title, content);
        return Post.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .user(user)
                .build();
    }

    public void updatePost(String newTitle, String newContent) {
        validate(newTitle, newContent);
        this.title = newTitle;
        this.content = newContent;
    }

    public static void validate(String title, String content) {
        validateBlank(title);
        validateTitleLength(title);

        validateBlank(content);
        validateContentLength(content);
    }

    public static void validateBlank(String text) {
        if (text == null || text.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }

    public static void validateTitleLength(String text) {
        if (TextLengthUtil.visibleLength(text) > PostPolicyConstant.TITLE_MAX_LENGTH.getValue()) {
            throw new BusinessException(ErrorCode.INVALID_TITLE_LENGTH_EXCEPTION);
        }
    }

    public static void validateContentLength(String text) {
        if (TextLengthUtil.visibleLength(text) > PostPolicyConstant.CONTENT_MAX_LENGTH.getValue()) {
            throw new BusinessException(ErrorCode.INVALID_CONTENT_LENGTH_EXCEPTION);
        }
    }

}