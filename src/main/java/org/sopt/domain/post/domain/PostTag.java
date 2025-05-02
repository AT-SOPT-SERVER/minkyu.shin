package org.sopt.domain.post.domain;

public enum PostTag {
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRASTRUCTURE("인프라");

    public final String tagName;

    PostTag(String tagName) {
        this.tagName = tagName;
    }

    String getTagName() {
        return tagName;
    }
}
