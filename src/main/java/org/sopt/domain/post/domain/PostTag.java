package org.sopt.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum PostTag {
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRASTRUCTURE("인프라"),
    ETC("기타");

    public final String tagName;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PostTag from(String tagName) {
        for (PostTag tag : PostTag.values()) {
            if (tag.name().equalsIgnoreCase(tagName)) {
                return tag;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }

}
