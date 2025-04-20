package org.sopt.domain.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;
    private String title;


    public Post() {}

    public Post(String title) {
        validateTitle(title);
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }


    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    private void validateTitle(String title) {
        if (isTitleBlank(title)) {
            throw new BusinessException(ErrorCode.POST_TITLE_BLANK_EXCEPTION);
        }
        if (isTitleOverLength(title)) {
            throw new BusinessException(ErrorCode.INVALID_TITLE_LENGTH_EXCEPTION);
        }
    }

    private boolean isTitleBlank(String title) {
        return title == null || title.isBlank();
    }

    private boolean isTitleOverLength(String title) {
//        return title.length() > 30;
        return title.codePointCount(0, title.length()) > 30;
    }

}