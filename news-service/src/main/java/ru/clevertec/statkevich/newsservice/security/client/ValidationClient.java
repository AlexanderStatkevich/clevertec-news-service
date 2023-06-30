package ru.clevertec.statkevich.newsservice.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "validationClient", url = "${news-service.user-service.uri}")
public interface ValidationClient {

    @GetMapping(value = "/validate")
    UsernamePasswordAuthenticationToken validate(@RequestParam("jwt") String jwt);

}
