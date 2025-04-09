package org.sopt.utils;

public class PostUtil {
    private static int postId = 0;

    public int generatePostId() {
        return postId++;
    }
}