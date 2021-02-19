package com.demo.system.util;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 使用guava缓存一些简单的数据
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@Slf4j
public class GuavaCacheUtil {

    private volatile static GuavaCacheUtil cacheUtil;

    private GuavaCacheUtil() {
    }

    public static GuavaCacheUtil getInstance() {
        if (cacheUtil == null) {
            synchronized (GuavaCacheUtil.class) {
                if (cacheUtil == null) {
                    cacheUtil = new GuavaCacheUtil();
                }
            }
        }
        return cacheUtil;
    }

    /**
     * initialCapacity:设置缓存容器的初始容量为10
     * maximumSize: 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
     * expireAfterAccess: 设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
     * recordStats: 开启Guava Cache的统计功能
     */
    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            // 当缓存项在指定的时间段内没有被读或写就会被回收。这种回收策略类似于基于容量回收策略；
            .expireAfterAccess(600, TimeUnit.SECONDS)   //缓存超时时间：10分钟
            //设置要统计缓存的命中率
            .recordStats()
            //设置并发级别为4，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(4)
            //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    log.info("{} was removed, cause is {}", notification.getKey(), notification.getCause());
                }
            })
            //build方法中可以指定CacheLoader，当缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(new CacheLoader<String, String>() {
                // 处理缓存键不存在缓存值时的处理逻辑
                @Override
                public String load(String key) throws Exception {
                    return null;   //返回空，不需要任何自动加载缓存的操作，如果需要也可以添加相应操作，如：数据库查找，redis等等
                }
            });

    /**
     * 添加缓存
     */
    public void addCache(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 获取缓存
     */
    public String getCache(String key) throws ExecutionException {
        return cache.get(key);
    }
}
