package ru.clevertec.statkevich.starterlogging.logging.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.statkevich.starterlogging.logging.aspect.LoggingAspect;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "custom.logging.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LoggingAspect.class)
    public LoggingAspect loggingAspect() {
        log.info("Logging Aspect is created...");
        return new LoggingAspect();
    }

}

