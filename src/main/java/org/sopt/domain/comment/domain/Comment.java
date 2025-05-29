package org.sopt.domain.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.user.domain.User;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 300)
    private String content;

    public static Comment create(final Post post, final User user, final String content) {
        validate(content);
        return Comment.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        validate(content);
        this.content = content;
    }

    private static void validate(final String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
        if (content.length() > 300) {
            throw new BusinessException(ErrorCode.INVALID_COMMENT_LENGTH_EXCEPTION);
        }
    }
}