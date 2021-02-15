package com.siec.system.modules.redis.service;

/**
 * @Description: redis发布接口
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
public interface RedisPubService {

    void publish(String key, String message);
}
