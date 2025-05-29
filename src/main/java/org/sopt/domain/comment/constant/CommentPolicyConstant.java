package org.sopt.domain.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentPolicyConstant {
    COMMENT_MAX_LENGTH(300);

    private final int value;

}
