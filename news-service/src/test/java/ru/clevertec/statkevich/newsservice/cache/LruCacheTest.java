package ru.clevertec.statkevich.newsservice.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LruCacheTest {

    public static final boolean ALLOW_NULL_VALUES = true;
    public static final int CACHE_CAPACITY = 100;
    public static final String CACHE_NAME = "cache_name";

    private final LruCache lruCache = new LruCache(ALLOW_NULL_VALUES, CACHE_NAME, CACHE_CAPACITY);

    @Test
    void checkGetNameReturnCorrectName() {
        String actual = lruCache.getName();
        assertThat(actual).isEqualTo(CACHE_NAME);
    }

    @Test
    void checkGetNativeCacheReturnNotNullValue() {
        Object nativeCache = lruCache.getNativeCache();
        assertThat(nativeCache).isNotNull();
    }

    @Test
    void checkPutAndLookupWorkCorrectly() {
        String expected = "value";
        lruCache.put(1L, expected);
        String actual = (String) lruCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void checkEvictIsDeletingElement() {
        String expected = "value";
        lruCache.put(1L, expected);
        String actual = (String) lruCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
        lruCache.evict(1L);
        String evicted = (String) lruCache.lookup(1L);
        assertThat(evicted).isNull();
    }


    @Test
    void checkClearIsWorking() {
        String expected = "value";
        lruCache.put(1L, expected);
        String actual = (String) lruCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
        lruCache.clear();
        String evicted = (String) lruCache.lookup(1L);
        assertThat(evicted).isNull();
    }

}
