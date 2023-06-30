package ru.clevertec.statkevich.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.clevertec.statkevich.userservice.validation.UniqueEntity;

public record UserRegistrationDto(

        @NotBlank
        @UniqueEntity
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        @JsonProperty(value = "mail")
        String email,
        @NotBlank
        @JsonProperty(value = "fio")
        String fullName,
        @NotBlank
        String password) {
}
