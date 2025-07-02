package org.sopt.domain.like.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.like.service.LikeCountFlushService;
import org.sopt.domain.like.service.LikeStatusFlushService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeFlushScheduler {

    private final LikeCountFlushService likeCountFlushService;
    private final LikeStatusFlushService likeStatusFlushService;

    @Scheduled(fixedRate = 1 * 60 * 1000) // 5분마다 실행
    public void flushLikeCountToDatabase() {
        likeCountFlushService.flushPostLikeCounts();
        likeCountFlushService.flushCommentLikeCounts();
    }

    @Scheduled(fixedRate = 1 * 60 * 1000) // 5분마다 실행
    public void flushLikeStatusToDatabase() {
        likeStatusFlushService.flushUserLikeStatus();
    }
}