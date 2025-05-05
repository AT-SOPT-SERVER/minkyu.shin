package org.sopt.global.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

public class InputValidator {
    private InputValidator() {}

    public static void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new BusinessException(ErrorCode.INPUT_BLANK_EXCEPTION);
        }
    }
}