package ru.clevertec.statkevich.newsservice.cache;

import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class LfuCacheManagerTest {

    private final LfuCacheManager lfuCacheManager = new LfuCacheManager();

    @Test
    void checkCacheCreatedAfterRequest() {
        Cache cache = lfuCacheManager.getCache("cache");
        assertThat(cache).isNotNull();
    }

    @Test
    void checkGetCacheNamesFilledAfterRequest() {
        lfuCacheManager.getCache("cache");
        Collection<String> cacheNames = lfuCacheManager.getCacheNames();
        assertThat(cacheNames.size()).isEqualTo(1);
    }
}
