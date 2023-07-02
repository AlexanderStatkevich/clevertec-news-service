package ru.clevertec.statkevich.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.clevertec.statkevich.userservice.domain.UserRole;
import ru.clevertec.statkevich.userservice.validation.UniqueEntity;
import ru.clevertec.statkevich.userservice.validation.UserRoleSubset;

public record UserRegistrationDto(

        @NotBlank
        @UniqueEntity
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        @JsonProperty(value = "mail")
        String email,
        @NotBlank
        @JsonProperty(value = "fio")
        String fullName,

        @UserRoleSubset(anyOf = {UserRole.JOURNALIST, UserRole.SUBSCRIBER})
        UserRole role,

        @NotBlank
        String password) {
}
