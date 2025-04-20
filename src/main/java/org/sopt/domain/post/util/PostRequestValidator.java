package org.sopt.domain.post.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostRequestValidator {

    public static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }
}