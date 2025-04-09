package org.sopt.domain;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String title;
    private final LocalDateTime createdAt;

    public Post(int id, String title) {
        validateTitle(title);
        this.id = id;
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

    public boolean changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
        return true;
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
        return title.length() > 30;
    }


}