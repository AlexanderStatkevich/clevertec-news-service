package ru.clevertec.statkevich.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.clevertec.statkevich.userservice.security.jwt.JwtProperty;

@EnableFeignClients
@EnableConfigurationProperties(JwtProperty.class)
@EnableAsync
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
