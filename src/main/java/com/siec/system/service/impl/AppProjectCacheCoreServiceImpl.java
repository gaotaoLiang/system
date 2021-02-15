package com.siec.system.service.impl;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.siec.system.common.constant.CacheFactory;
import com.siec.system.common.constant.CommonConstant;
import com.siec.system.model.po.AppProjectPo;
import com.siec.system.modules.redis.service.RedisPubService;
import com.siec.system.modules.redis.util.RedisUtil;
import com.siec.system.service.AppProjectCacheCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.siec.system.common.constant.CommonConstant.DEFAULT_GUAVA_CACHE_DURATION_IN_MIN;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@Slf4j
@Service
public class AppProjectCacheCoreServiceImpl implements AppProjectCacheCoreService {

    @Autowired
    private RedisPubService redisPubService;

    @Autowired
    private RedisUtil redisUtil;

    //不同用户申请项目缓存
    private LoadingCache<String, AppProjectPo> appProjectUsernameLoadingCache;


    //初始化guava缓存
    @PostConstruct
    public void initLoadingCache() {
        this.appProjectUsernameLoadingCache = initAppProjectUsernameLoadingCache();
    }

    private LoadingCache<String, AppProjectPo> initAppProjectUsernameLoadingCache() {
        return CacheFactory.create(new CacheLoader<String, AppProjectPo>() {
            @Override
            public AppProjectPo load(String s) throws Exception {
                return null;
            }
        }, DEFAULT_GUAVA_CACHE_DURATION_IN_MIN);
    }


    @Override
    public void deleteAllLoadingCache() {
        this.appProjectUsernameLoadingCache.invalidateAll();
    }

    @Override
    public void deleteAppProjectCacheCode(String cacheCode) {
        this.appProjectUsernameLoadingCache.invalidate(cacheCode);
    }

    //发布
    @Override
    public void deleteAppProjectCacheUsePublish(String cacheCode) {
        redisPubService.publish(CommonConstant.APP_PROJECT_ALL_CACHE, cacheCode);
    }
}
