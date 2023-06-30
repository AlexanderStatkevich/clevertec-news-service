package ru.clevertec.statkevich.userservice.account;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.clevertec.statkevich.userservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.dto.UserLoginDto;
import ru.clevertec.statkevich.userservice.dto.UserRegistrationDto;

public interface IUserAccountService {
    void register(UserRegistrationDto userRegistrationDto);

    boolean verify(EmailVerificationDto emailVerificationDto);

    String login(UserLoginDto userLoginDto);

    UserDto getInfo();

    UsernamePasswordAuthenticationToken validate(String jwt);
}
