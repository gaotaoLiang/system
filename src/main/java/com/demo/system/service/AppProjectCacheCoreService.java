package com.demo.system.service;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
public interface AppProjectCacheCoreService {

    void deleteAllLoadingCache();

    void deleteAppProjectCacheCode(String cacheCode);

    void deleteAppProjectCacheUsePublish(String cacheCode);
}
