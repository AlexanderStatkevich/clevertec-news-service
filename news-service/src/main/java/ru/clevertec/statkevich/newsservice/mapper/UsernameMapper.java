package ru.clevertec.statkevich.newsservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public abstract class UsernameMapper {

    @Named(value = "getUsername")
    public String getUsername(Object object) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }
}
