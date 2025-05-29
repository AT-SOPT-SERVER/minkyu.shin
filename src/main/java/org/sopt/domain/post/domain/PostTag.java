package org.sopt.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum PostTag {
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRASTRUCTURE("인프라");

    public final String tagName;

    @JsonCreator
    public static PostTag from(String tagName) {
        for (PostTag tag : PostTag.values()) {
            if (tag.name().equalsIgnoreCase(tagName)) {
                return tag;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
