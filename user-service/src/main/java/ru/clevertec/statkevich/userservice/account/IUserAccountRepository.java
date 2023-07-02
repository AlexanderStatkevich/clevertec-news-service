package ru.clevertec.statkevich.userservice.account;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.statkevich.userservice.domain.User;

import java.util.UUID;

@Repository
public interface IUserAccountRepository extends CrudRepository<User, UUID> {
    User findUserByEmail(String email);

    @Modifying
    @Query("update User u set status = ru.clevertec.statkevich.userservice.domain.UserStatus.ACTIVATED where email = ?1")
    void activateUserByEmail(String email);
}
