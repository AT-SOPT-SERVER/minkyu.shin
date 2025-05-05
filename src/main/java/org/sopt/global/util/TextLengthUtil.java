package org.sopt.global.util;

public class TextLengthUtil {

    private static final int ZWJ = 0x200D;

    private TextLengthUtil() {}

    public static int visibleLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            i += Character.charCount(codePoint);

            // ex: 🧑‍🧑‍🧒‍🧒: 이모지 + ZWJ + 이모지 + ZWJ + 이모지 + ZWJ + 이모지
            if (isZWJ(codePoint)) {
                length--;
                continue;
            }

            // ex: 👦🏼: 이모지 + 수정자
            if (isEmojiModifier(codePoint)) {
                continue;
            }

            length++;
        }

        return length;
    }

    private static boolean isZWJ(int codePoint) {
        return codePoint == ZWJ;
    }

    private static boolean isEmojiModifier(int codePoint) {
        return 0x1F3FB <= codePoint && codePoint <= 0x1F3FF;
    }
}