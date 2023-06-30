package ru.clevertec.statkevich.userservice.manage;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.statkevich.userservice.domain.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserManageRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> getByUuid(UUID uuid);
}
