package org.sopt.domain;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String title;
    private final LocalDateTime createdAt;

    public Post(int id, String title) {
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

    public boolean changeTitle(String newTitle) {
        this.title = newTitle;
        return true;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}