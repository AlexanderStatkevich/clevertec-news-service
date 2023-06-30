package ru.clevertec.statkevich.userservice.account;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.userservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.dto.UserLoginDto;
import ru.clevertec.statkevich.userservice.dto.UserRegistrationDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserAccountController {

    private final IUserAccountService userAccountService;


    @PostMapping(path = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        userAccountService.register(userRegistrationDto);
    }

    @GetMapping(path = "/verification")
    @ResponseStatus(value = HttpStatus.OK)
    public void verify(EmailVerificationDto emailVerificationDto) {
        userAccountService.verify(emailVerificationDto);
    }

    @PostMapping(path = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    public String login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userAccountService.login(userLoginDto);
    }

    @GetMapping(path = "/me")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto getPersonalInfo() {
        return userAccountService.getInfo();
    }

    @GetMapping(path = "/validate")
    @ResponseStatus(value = HttpStatus.OK)
    public UsernamePasswordAuthenticationToken validate(@RequestParam("jwt") String jwt) {
        return userAccountService.validate(jwt);
    }

}
