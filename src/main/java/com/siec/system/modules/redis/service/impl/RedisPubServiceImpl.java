package com.siec.system.modules.redis.service.impl;

import com.siec.system.modules.redis.service.RedisPubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
@Slf4j
@Service
public class RedisPubServiceImpl implements RedisPubService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
