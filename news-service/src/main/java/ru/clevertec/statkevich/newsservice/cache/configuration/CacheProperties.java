package ru.clevertec.statkevich.newsservice.cache.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("news.cache")
public record CacheProperties(
        @NotNull
        CacheType type
) {
}
