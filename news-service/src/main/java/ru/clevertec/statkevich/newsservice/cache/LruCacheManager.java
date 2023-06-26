package ru.clevertec.statkevich.newsservice.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LruCacheManager implements CacheManager {
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>();


    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (null == cache) {
            cache = new LruCache(true, name, 100);
            cacheMap.put(name, cache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}
