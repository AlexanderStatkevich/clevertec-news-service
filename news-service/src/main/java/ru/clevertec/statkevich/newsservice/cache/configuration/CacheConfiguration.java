package ru.clevertec.statkevich.newsservice.cache.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import ru.clevertec.statkevich.newsservice.cache.LfuCacheManager;
import ru.clevertec.statkevich.newsservice.cache.LruCacheManager;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(CacheProperties cacheProperties, RedisConnectionFactory connectionFactory) {
        CacheType type = cacheProperties.type();
        return switch (type) {
            case LFU -> new LfuCacheManager();
            case LRU -> new LruCacheManager();
            case REDIS -> RedisCacheManager.create(connectionFactory);
        };
    }
}
