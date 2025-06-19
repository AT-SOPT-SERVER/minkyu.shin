package org.sopt.domain.user.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class ValidationUtils {

    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&^]).{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.][0-9A-Za-z]+$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static void validatePasswordFormat(String input, ErrorCode errorCode) {
        if(!PASSWORD_PATTERN.matcher(input).matches()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void validateEmailFormat(String input, ErrorCode errorCode) {
        if (!EMAIL_PATTERN.matcher(input).matches()) {
            throw new BusinessException(errorCode);
        }
    }
}
