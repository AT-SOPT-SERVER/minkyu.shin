package org.sopt.domain.post.constant;

public enum PostPolicyConstant {
    TITLE_MAX_LENGTH(30),
    POST_DELAY_SECONDS(180);

    private final int value;

    PostPolicyConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
