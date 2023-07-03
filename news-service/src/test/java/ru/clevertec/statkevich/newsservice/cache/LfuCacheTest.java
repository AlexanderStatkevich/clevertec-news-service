package ru.clevertec.statkevich.newsservice.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LfuCacheTest {

    public static final boolean ALLOW_NULL_VALUES = true;
    public static final int CACHE_CAPACITY = 100;
    public static final String CACHE_NAME = "cache_name";

    private final LfuCache lfuCache = new LfuCache(ALLOW_NULL_VALUES, CACHE_CAPACITY, CACHE_NAME);

    @Test
    void checkGetNameReturnCorrectName() {
        String actual = lfuCache.getName();
        assertThat(actual).isEqualTo(CACHE_NAME);
    }

    @Test
    void checkGetNativeCacheReturnNotNullValue() {
        Object nativeCache = lfuCache.getNativeCache();
        assertThat(nativeCache).isNotNull();
    }

    @Test
    void checkPutAndLookupWorkCorrectly() {
        String expected = "value";
        lfuCache.put(1L, expected);
        String actual = (String) lfuCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void checkEvictIsDeletingElement() {
        String expected = "value";
        lfuCache.put(1L, expected);
        String actual = (String) lfuCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
        lfuCache.evict(1L);
        String evicted = (String) lfuCache.lookup(1L);
        assertThat(evicted).isNull();
    }


    @Test
    void checkClearIsWorking() {
        String expected = "value";
        lfuCache.put(1L, expected);
        String actual = (String) lfuCache.lookup(1L);
        assertThat(expected).isEqualTo(actual);
        lfuCache.clear();
        String evicted = (String) lfuCache.lookup(1L);
        assertThat(evicted).isNull();
    }

}
