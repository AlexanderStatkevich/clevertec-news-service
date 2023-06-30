package ru.clevertec.statkevich.emailservice.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.emailservice.dto.EmailDto;
import ru.clevertec.statkevich.emailservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.emailservice.service.EmailService;


@RestController
@RequestMapping("/api/v1/email")
public class EmailServiceController {

    private final EmailService service;

    public EmailServiceController(EmailService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public void sendVerificationCode(@Valid @RequestBody EmailDto emailDto) {
        service.send(emailDto);
    }

    @GetMapping("/verify")
    public Boolean verifyEmail(@Valid EmailVerificationDto emailVerificationDto) {
        return service.verify(emailVerificationDto);
    }
}
