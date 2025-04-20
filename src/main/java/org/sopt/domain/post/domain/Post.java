package org.sopt.domain.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.sopt.domain.post.util.TextLengthUtil;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;


    public Post() {}

    public Post(String title) {
        validateTitle(title);
        this.title = title;
    }

    public static Post createPost(String title) {
        return new Post(title);
    }

    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    private void validateTitle(String title) {
        validateBlank(title);
        validateLength(title);
    }

    private void validateBlank(String text) {
        if (text == null || text.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }

    private void validateLength(String text) {
        if (TextLengthUtil.visibleLength(text) > 30) {
            throw new BusinessException(ErrorCode.INVALID_TITLE_LENGTH_EXCEPTION);
        }
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

}