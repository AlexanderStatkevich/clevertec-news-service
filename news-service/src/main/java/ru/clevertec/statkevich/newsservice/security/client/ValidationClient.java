package ru.clevertec.statkevich.newsservice.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.statkevich.newsservice.dto.user.UserAuthorityDto;

/**
 * Client for requesting validation of JSON Web Token
 */

@FeignClient(value = "validationClient", url = "${settings.user-service.uri}")
public interface ValidationClient {

    @GetMapping(value = "/validate")
    UserAuthorityDto validate(@RequestParam("jwt") String jwt);

}
