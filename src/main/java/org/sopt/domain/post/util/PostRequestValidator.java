package org.sopt.domain.post.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostRequestValidator {

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.POST_TITLE_BLANK_EXCEPTION);
        }
    }
}