package ru.clevertec.statkevich.newsservice.cache.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the cache abstraction.
 *
 * @param type represent the selected cache type
 */
@Validated
@ConfigurationProperties("news-service.cache")
public record CacheProperties(
        @NotNull
        CacheType type
) {
}
