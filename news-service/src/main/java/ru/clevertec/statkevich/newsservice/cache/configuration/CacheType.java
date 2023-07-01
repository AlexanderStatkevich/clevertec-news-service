package ru.clevertec.statkevich.newsservice.cache.configuration;

/**
 * Supported cache types.
 */
public enum CacheType {

    /**
     * LFU (Least Frequently Used) caching.
     */
    LFU,

    /**
     * LRU (Least Recently Used) caching.
     */
    LRU,

    /**
     * Redis backed caching.
     */
    REDIS
}
