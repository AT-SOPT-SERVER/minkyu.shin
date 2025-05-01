package org.sopt.domain.user.constant;

public enum UserPolicyConstant {

    NICKNAME_MAX_LENGTH(20);

    private final int value;

    UserPolicyConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
