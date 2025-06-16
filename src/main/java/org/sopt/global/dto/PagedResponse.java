package org.sopt.global.dto;

import java.util.List;

public record PagedResponse<T> (
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last // 무한 스크롤이나 페이지네이션 과정에서 다음 페이지가 있는지 알리기 위함
) {
}