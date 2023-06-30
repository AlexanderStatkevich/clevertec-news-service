package ru.clevertec.statkevich.userservice.dto;

import java.util.Map;

public record EmailDto(
        String email,
        Map<String, Object> model
) {
}
