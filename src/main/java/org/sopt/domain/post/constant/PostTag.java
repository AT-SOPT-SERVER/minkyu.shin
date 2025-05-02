package org.sopt.domain.post.constant;

public enum PostTag {
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRASTRUCTURE("인프라");

    private final String tagName;

    PostTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
