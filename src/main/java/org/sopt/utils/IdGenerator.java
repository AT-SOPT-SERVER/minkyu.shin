package org.sopt.utils;

public class IdGenerator {
    private static int postId = 1;

    public int generatePostId() {
        return postId++;
    }
}