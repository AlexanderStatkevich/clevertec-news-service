package ru.clevertec.statkevich.userservice.manage;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.userservice.domain.User;
import ru.clevertec.statkevich.userservice.dto.UserCreateUpdateDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/users")
public class UserManageController {

    private final IUserManageService userManageService;
    private final UserMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCreateUpdateDto userCreateUpdateDto) {
        userManageService.create(userCreateUpdateDto);
    }

    @GetMapping
    public Page<UserDto> getList(Pageable pageable) {
        Page<User> userPage = userManageService.findAll(pageable);
        return userPage.map(mapper::toDto);
    }

    @GetMapping(path = "/{uuid}")
    public UserDto getById(@NotBlank @PathVariable UUID uuid) {
        User user = userManageService.findById(uuid);
        return mapper.toDto(user);
    }

    @PutMapping(path = "/{uuid}/dt_update/{dt_update}")
    public void update(@NotBlank @PathVariable("uuid") UUID uuid,
                       @NotBlank @PathVariable("dt_update") LocalDateTime dateTimeUpdate,
                       @Valid @RequestBody UserCreateUpdateDto userCreateUpdateDto) {
        userManageService.update(uuid, dateTimeUpdate, userCreateUpdateDto);
    }
}
