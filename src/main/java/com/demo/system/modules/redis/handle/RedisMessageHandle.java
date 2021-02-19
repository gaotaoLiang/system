package com.demo.system.modules.redis.handle;

import com.demo.system.modules.redis.model.RedisMessage;

/**
 * @Description: redis订阅发布-处理器
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
public interface RedisMessageHandle {
    void handle(RedisMessage redisMessage);
}
