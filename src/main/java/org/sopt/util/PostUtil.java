package org.sopt.util;

public class PostUtil {
    private static int postId = 0;

    public int generatePostId() {
        return postId++;
    }
}