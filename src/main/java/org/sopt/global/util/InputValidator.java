package org.sopt.global.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

public class InputValidator {
    private InputValidator() {}

    public static void validateNullOrBlank(String input, ErrorCode errorCode) {
        if (input == null || input.isBlank()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void validateLength(String input, int minLength, int maxLength, ErrorCode errorCode) {
        if (input.length() < minLength || input.length() > maxLength) {
            throw new BusinessException(errorCode);
        }
    }

    public static void validateInvisibleLength(String input, int minLength, int maxLength, ErrorCode errorCode) {
        int visibleLength = TextLengthUtil.visibleLength(input);
        if (visibleLength < minLength || visibleLength > maxLength) {
            throw new BusinessException(errorCode);
        }
    }
}