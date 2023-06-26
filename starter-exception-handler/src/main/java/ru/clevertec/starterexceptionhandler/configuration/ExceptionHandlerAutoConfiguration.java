package ru.clevertec.starterexceptionhandler.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.starterexceptionhandler.exception.GlobalExceptionHandler;

@Configuration
public class ExceptionHandlerAutoConfiguration {


    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
