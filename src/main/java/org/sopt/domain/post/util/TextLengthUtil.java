package org.sopt.domain.post.util;

import java.text.BreakIterator;
import java.util.Locale;

public class TextLengthUtil {
    private TextLengthUtil() {}

    public static int visibleLength(String text) {
        BreakIterator breakIterator = BreakIterator.getCharacterInstance(Locale.ROOT);
        breakIterator.setText(text);

        int length = 0;
        while (breakIterator.next() != BreakIterator.DONE) {
            length++;
        }

        return length;
    }
}