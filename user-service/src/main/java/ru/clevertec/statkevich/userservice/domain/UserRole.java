package ru.clevertec.statkevich.userservice.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN,
    JOURNALIST,
    SUBSCRIBER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
