package org.sopt.domain.post.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

public class PostRequestValidator {
    private PostRequestValidator() {}

    public static void validateNull(String input) {
        if (input == null || input.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }
}