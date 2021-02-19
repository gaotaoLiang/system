package com.demo.system.handle;

import com.demo.system.common.constant.CommonConstant;
import com.demo.system.modules.redis.handle.RedisMessageHandle;
import com.demo.system.modules.redis.listener.RedisSubListener;
import com.demo.system.modules.redis.model.RedisMessage;
import com.demo.system.service.AppProjectCacheCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description: 项目redis订阅发布模式监听内容
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
@Slf4j
@Component
public class AppProjectSubHandle implements RedisMessageHandle {

    @Autowired
    private AppProjectCacheCoreService appProjectCacheCoreService;

    @Autowired
    private RedisSubListener redisSubListener;

    @PostConstruct
    public void init() {
      //通过key监听，相当于订阅
        redisSubListener.registryHandle(CommonConstant.APP_PROJECT_ALL_CACHE, this);
    }

    @Override
    public void handle(RedisMessage redisMessage) {

    }
}
