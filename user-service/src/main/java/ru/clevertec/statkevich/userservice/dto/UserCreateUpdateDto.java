package ru.clevertec.statkevich.userservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.clevertec.statkevich.userservice.domain.UserRole;
import ru.clevertec.statkevich.userservice.domain.UserStatus;
import ru.clevertec.statkevich.userservice.validation.UniqueEntity;

public record UserCreateUpdateDto(
        @NotBlank
        @UniqueEntity
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        @JsonProperty(value = "mail")
        String email,
        @NotBlank
        @JsonProperty(value = "fio")
        String fullName,

        UserRole role,

        UserStatus status,
        @NotBlank
        String password
) {
}
