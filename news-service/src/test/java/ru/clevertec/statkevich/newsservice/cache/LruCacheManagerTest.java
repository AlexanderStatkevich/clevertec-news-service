package ru.clevertec.statkevich.newsservice.cache;

import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class LruCacheManagerTest {

    private final LruCacheManager lruCacheManager = new LruCacheManager();

    @Test
    void checkCacheCreatedAfterRequest() {
        Cache cache = lruCacheManager.getCache("cache");
        assertThat(cache).isNotNull();
    }

    @Test
    void checkGetCacheNamesFilledAfterRequest() {
        lruCacheManager.getCache("cache");
        Collection<String> cacheNames = lruCacheManager.getCacheNames();
        assertThat(cacheNames.size()).isEqualTo(1);
    }
}
