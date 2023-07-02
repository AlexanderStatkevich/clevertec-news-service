package ru.clevertec.statkevich.userservice.account;


import ru.clevertec.statkevich.userservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.userservice.dto.UserAuthorityDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.dto.UserLoginDto;
import ru.clevertec.statkevich.userservice.dto.UserRegistrationDto;

public interface IUserAccountService {
    void register(UserRegistrationDto userRegistrationDto);

    boolean verify(EmailVerificationDto emailVerificationDto);

    String login(UserLoginDto userLoginDto);

    UserDto getInfo();

    UserAuthorityDto validate(String jwt);
}
