package com.siec.system.modules.redis.listener;

import com.siec.system.modules.redis.handle.RedisMessageHandle;
import org.springframework.stereotype.Component;

/**
 * @Description: redis订阅监听器接口
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
@Component
public interface RedisSubListener {

    void receiveMessage(String message, String channel);

    void registryHandle(String type, RedisMessageHandle... handles);
}
