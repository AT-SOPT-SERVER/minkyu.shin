package org.sopt.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.domain.post.constant.PostPolicyConstant;
import org.sopt.global.util.TextLengthUtil;
import org.sopt.domain.user.domain.User;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "post_title_idx", columnList = "title", unique = true),
                @Index(name = "post_user_id_idx", columnList = "user_id"),
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

    /**
     * Post 엔티티의 id와 연결되는 FK `post_id`를 갖는 테이블(`post_tags`) 생성
     */
    @ElementCollection(targetClass = PostTag.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            indexes = @Index(name = "post_tags_tag_idx", columnList = "tag")
    )
    @Column(name = "tag", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PostTag> tags = new HashSet<>();

    @Column(nullable = false)
    private int likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Post create(String title, String content, Set<PostTag> tags, User user) {
        validate(title, content, tags);
        return Post.builder()
                .title(title)
                .content(content)
                .tags(tags)
                .user(user)
                .build();
    }

    public void updatePost(String newTitle, String newContent, Set<PostTag> newTags) {
        validate(newTitle, newContent, newTags);
        this.title = newTitle;
        this.content = newContent;
        this.tags = newTags;
    }

    public static void validate(String title, String content, Set<PostTag> tags) {
        validateBlank(title);
        validateTitleLength(title);

        validateBlank(content);
        validateContentLength(content);

        validateTags(tags);
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

    private static void validateTags(Set<PostTag> tags) {
        if (tags.size() > PostPolicyConstant.TAG_MAX_COUNT.getValue()) {
            throw new BusinessException(ErrorCode.TAG_COUNT_LIMIT_EXCEPTION);
        }
    }

}