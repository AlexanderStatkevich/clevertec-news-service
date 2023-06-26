package ru.clevertec.starterexceptionhandler.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.starterexceptionhandler.exception.GlobalExceptionHandler;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "custom.exception-handler.enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionHandlerAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        log.info("Global handler bean is created...");
        return new GlobalExceptionHandler();
    }

}
