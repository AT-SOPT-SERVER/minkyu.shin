package org.sopt.domain.post.constant;

import org.springframework.data.domain.Sort;

public enum PostSortType {
    LATEST("createdAt");

    private final String sortField;

    PostSortType(String sortField) {
        this.sortField = sortField;
    }

    public Sort getSort() {
        return Sort.by(Sort.Direction.DESC, this.sortField);
    }
}
