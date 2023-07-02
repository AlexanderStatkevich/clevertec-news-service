package ru.clevertec.statkevich.userservice.account;


import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.statkevich.userservice.domain.User;
import ru.clevertec.statkevich.userservice.domain.UserStatus;
import ru.clevertec.statkevich.userservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.userservice.dto.UserAuthorityDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.dto.UserLoginDto;
import ru.clevertec.statkevich.userservice.dto.UserRegistrationDto;
import ru.clevertec.statkevich.userservice.mailing.UserAccountClient;
import ru.clevertec.statkevich.userservice.mailing.UserRegisteredEvent;
import ru.clevertec.statkevich.userservice.mapper.UserMapper;
import ru.clevertec.statkevich.userservice.security.UserHolder;
import ru.clevertec.statkevich.userservice.security.jwt.JwtTokenProcessor;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserAccountService implements IUserAccountService, UserDetailsService {
    private final IUserAccountRepository repository;
    private final UserAccountClient userAccountClient;
    private final JwtTokenProcessor jwtTokenProcessor;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserHolder userHolder;
    private final ApplicationEventPublisher applicationEventPublisher;


    public UserAccountService(IUserAccountRepository repository,
                              UserAccountClient userAccountClient,
                              JwtTokenProcessor jwtTokenProcessor,
                              PasswordEncoder passwordEncoder,
                              UserMapper userMapper,
                              UserHolder userHolder,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.userAccountClient = userAccountClient;
        this.jwtTokenProcessor = jwtTokenProcessor;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userHolder = userHolder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        User user = userMapper.toEntity(userRegistrationDto);
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
        user.setStatus(UserStatus.WAITING_ACTIVATION);
        repository.save(user);
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(user));
    }

    @Transactional
    @Override
    public boolean verify(@Valid EmailVerificationDto emailVerificationDto) {
        String email = emailVerificationDto.mail();
        String code = emailVerificationDto.code();
        Boolean verify = userAccountClient.verify(email, code);
        if (verify) {
            repository.activateUserByEmail(email);
        }
        return verify;
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String email = userLoginDto.email();
        String enteredPassword = userLoginDto.password();
        User user = repository.findUserByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("user does not exist");
        }
        if (user.getStatus() != UserStatus.ACTIVATED) {
            throw new AccessDeniedException("user does not activated yet");
        }
        String actualPassword = user.getPassword();
        if (!passwordEncoder.matches(enteredPassword, actualPassword)) {
            throw new BadCredentialsException("invalid password");
        }
        return jwtTokenProcessor.generateAccessToken(user);
    }

    @Override
    public UserDto getInfo() {
        UserDetails userDetails = userHolder.getUser();
        String username = userDetails.getUsername();
        User user = repository.findUserByEmail(username);
        return userMapper.toDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(user.getRole()));
    }

    @Override
    public UserAuthorityDto validate(String jwt) {
        boolean validated = jwtTokenProcessor.validate(jwt);
        if (!validated) {
            throw new AccessDeniedException("access restricted");
        }
        String username = jwtTokenProcessor.getUsername(jwt);
        UserDetails userDetails = loadUserByUsername(username);
        return userDetails.getAuthorities()
                .stream()
                .map(Object::toString)
                .map(auth -> new UserAuthorityDto(username, auth))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("role doesn't present"));
    }
}
