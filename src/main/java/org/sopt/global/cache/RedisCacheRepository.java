package org.sopt.global.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisCacheRepository implements CacheRepository {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    // Redis에서 해당 키의 값을 가져오고, JSON → 객체로 변환
    // 캐시에 없으면 null 반환
    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) return null;
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 캐시가 없을 경우 supplier로 값을 생성하고 Redis에 저장
    // 있으면 바로 캐시 값 반환 -> lazy caching
    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz, Supplier<T> supplier) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                T value = Objects.requireNonNull(supplier.get());
                set(key, value);
                return value;
            }
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 캐시를 JSON 문자열로 직렬화해서 Redis에 저장(ttl 없음)
    @Override
    public <T extends Serializable> void set(String key, T valueObject) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(valueObject));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // TTL과 함께 Redis에 저장
    @Override
    public <T extends Serializable> void set(String key, T valueObject, int timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(valueObject), timeout, timeUnit);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Set<String> findKeys(String pattern) {
        Set<String> keys = new HashSet<>();
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions()
                    .match(pattern)
                    .count(1000)
                    .build())) {

                while (cursor.hasNext()) {
                    keys.add(new String(cursor.next()));
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.REDIS_OPERATION_FAILED);
            }

            return null;
        });
        return keys;
    }
}