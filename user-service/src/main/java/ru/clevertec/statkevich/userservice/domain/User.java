package ru.clevertec.statkevich.userservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntityWithDateTime {
    @Column(name = "email")
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    @Column(name = "password")
    private String password;

}
