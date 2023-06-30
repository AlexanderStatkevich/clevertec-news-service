package ru.clevertec.statkevich.userservice.manage;


import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.userservice.domain.User;
import ru.clevertec.statkevich.userservice.dto.UserCreateUpdateDto;
import ru.clevertec.statkevich.userservice.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UserManageService implements IUserManageService {

    public static final int ZONE_OFFSET = 3;
    private final IUserManageRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserManageService(IUserManageRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userMapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserCreateUpdateDto userCreateUpdateDto) {
        User user = userMapper.toEntity(userCreateUpdateDto);
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userCreateUpdateDto.password()));
        repository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public User findById(UUID uuid) {
        return repository.getReferenceById(uuid);
    }

    @Override
    public void update(UUID uuid, LocalDateTime dateTimeUpdate, UserCreateUpdateDto userCreateUpdateDto) {
        User user = repository.getByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (!user.getDateTimeUpdate().truncatedTo(ChronoUnit.MILLIS).equals(dateTimeUpdate.minusHours(ZONE_OFFSET))) {
            throw new OptimisticLockException("user with " + uuid + " has been modified");
        }
        userMapper.map(userCreateUpdateDto, user);
        user.setDateTimeUpdate(dateTimeUpdate);
        repository.save(user);
    }

    public boolean notExistsByEmail(String email) {
        return !repository.existsByEmail(email);
    }
}
