package org.sopt.global.cache;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface CacheRepository {

    <T extends Serializable> T get(String key, Class<T> clazz);

    <T extends Serializable> T get(String key, Class<T> clazz, Supplier<T> supplier);

    <T extends Serializable> void set(String key, T valueObject);

    <T extends Serializable> void set(String key, T valueObject, int timeout, TimeUnit timeUnit);

    void delete(String key);

    Set<String> findKeys(String pattern);
}