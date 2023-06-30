package ru.clevertec.statkevich.newsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableMethodSecurity
@EnableCaching
@EnableTransactionManagement
@EnableFeignClients
@SpringBootApplication
public class NewsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsServiceApplication.class, args);
    }

}
