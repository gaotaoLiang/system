package com.siec.system.common.constant;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

/**
 * @Description: guava缓存工厂
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
public final class CacheFactory {

    private CacheFactory() {
    }

    public static <K, V> LoadingCache<K, V> create(CacheLoader<K, V> loader) {
        return create(loader, 10L);
    }

    public static <K, V> LoadingCache<K, V> create(CacheLoader loader, long durationMins) {
        return CacheBuilder.newBuilder().refreshAfterWrite(durationMins, TimeUnit.MINUTES).build(loader);
    }

    public static <K, V> LoadingCache<K, V> create(CacheLoader loader, RemovalListener removalListener, long durationMins) {
        return CacheBuilder.newBuilder().refreshAfterWrite(durationMins, TimeUnit.MINUTES).removalListener(removalListener).build(loader);
    }
}
