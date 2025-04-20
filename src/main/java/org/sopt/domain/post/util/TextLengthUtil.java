package org.sopt.domain.post.util;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.text.BreakIterator;
import java.util.Locale;

@Component
public class TextLengthUtil {

    public static int visibleLength(String text) {
        BreakIterator breakIterator = BreakIterator.getCharacterInstance(Locale.ROOT);
        breakIterator.setText(text);

        int cnt = 0;
        while (breakIterator.next() != BreakIterator.DONE) {
            cnt++;
        }

        return cnt;
    }
}