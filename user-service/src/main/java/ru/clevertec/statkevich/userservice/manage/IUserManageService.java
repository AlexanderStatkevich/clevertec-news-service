package ru.clevertec.statkevich.userservice.manage;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.statkevich.userservice.domain.User;
import ru.clevertec.statkevich.userservice.dto.UserCreateUpdateDto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserManageService {
    void create(UserCreateUpdateDto userCreateUpdateDto);

    Page<User> findAll(Pageable pageable);

    User findById(UUID uuid);

    void update(UUID uuid, LocalDateTime dateTimeUpdate, UserCreateUpdateDto userCreateUpdateDto);
}
