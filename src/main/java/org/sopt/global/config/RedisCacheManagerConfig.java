package org.sopt.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.global.cache.CacheType;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisCacheManagerConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * 단순 조회 + TTL 기반 캐시는 @Cacheable + RedisCacheManagerConfig 사용
     * TTL보다 데이터 정확성과 실시간 동기화가 더 중요한 경우엔 도메인 별
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 캐시 이름별 TTL 개별 설정
        cacheConfigurations.put(CacheType.POST_LIST.getName(), configWithTTL(CacheType.POST_LIST.getTtl())); // 10분 TTL
        cacheConfigurations.put(CacheType.POST_DETAIL.getName(), configWithTTL(CacheType.POST_DETAIL.getTtl())); // 15분 TTL

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig(Duration.ofMinutes(5))) // 기본 TTL
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    // 기본 캐시 설정
    private RedisCacheConfiguration defaultCacheConfig(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues()
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    // 개별 TTL 설정 헬퍼 메서드
    private RedisCacheConfiguration configWithTTL(Duration ttl) {
        return defaultCacheConfig(ttl);
    }
}