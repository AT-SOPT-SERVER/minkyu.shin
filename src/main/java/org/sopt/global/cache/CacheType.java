package org.sopt.global.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    POST_LIST("postList", Duration.ofMinutes(10)),
    POST_DETAIL("postDetail", Duration.ofMinutes(15));

    private final String name;
    private final Duration ttl;
}