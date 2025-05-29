package org.sopt.domain.post.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostPolicyConstant {
    TITLE_MAX_LENGTH(30),
    CONTENT_MAX_LENGTH(1000),
    POST_DELAY_SECONDS(180);

    private final int value;

}
