package org.sopt.domain.post.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Post {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    private final LocalDateTime createdAt;

    public Post(String title) {
        validateTitle(title);
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    private void validateTitle(String title) {
        if (isTitleBlank(title)) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if (isTitleOverLength(title)) {
            throw new IllegalArgumentException("제목은 30자를 넘을 수 없습니다.");
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