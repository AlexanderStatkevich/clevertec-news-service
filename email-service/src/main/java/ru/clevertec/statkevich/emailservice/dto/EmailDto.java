package ru.clevertec.statkevich.emailservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Map;


public record EmailDto(
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        @NotBlank
        String email,
        Map<String, Object> model
) {
}
