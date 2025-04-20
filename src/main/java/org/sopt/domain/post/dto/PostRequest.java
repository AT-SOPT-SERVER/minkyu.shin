package org.sopt.domain.post.dto;

public class PostRequest {
    private String title;

    public PostRequest() {}

    PostRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}