package ru.clevertec.statkevich.userservice.mailing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.statkevich.userservice.dto.EmailDto;


@FeignClient(value = "userAccountClient", url = "${user-service.mail-service.uri}")
public interface UserAccountClient {
    @GetMapping(value = "/verify")
    Boolean verify(@RequestParam("email") String email, @RequestParam("code") String code);

    @PostMapping(value = "/send", produces = "application/json")
    void send(@RequestBody EmailDto emailDto);
}

