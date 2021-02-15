package com.siec.system.service.impl;

import com.siec.system.common.constant.ResultStatus;
import com.siec.system.common.exception.UnexpectedStatusException;
import com.siec.system.modules.redis.util.RedisUtil;
import com.siec.system.service.AppProjectCacheCoreService;
import com.siec.system.service.AppProjectCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
@Service
@Slf4j
public class AppProjectCacheServiceImpl implements AppProjectCacheService {

    @Autowired
    private AppProjectCacheCoreService appProjectCacheCoreService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void deleteAppProjectCache(String cacheCode) {
        try {
            if (StringUtils.isEmpty(cacheCode)) {
                return;
            }
            redisUtil.del(cacheCode);
            appProjectCacheCoreService.deleteAppProjectCacheUsePublish(cacheCode);
        } catch (Exception e) {
            log.info("AppProjectCacheServiceImpl deleteAppProjectCache error: {}", e.getMessage(), e);
            throw new UnexpectedStatusException(ResultStatus.CACHE_DELETE_FAILURE);
        }
    }

































}
