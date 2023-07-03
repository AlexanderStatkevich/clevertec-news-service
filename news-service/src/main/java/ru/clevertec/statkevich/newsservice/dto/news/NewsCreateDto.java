package ru.clevertec.statkevich.newsservice.dto.news;

import jakarta.validation.constraints.NotBlank;

public record NewsCreateDto(

        @NotBlank
        String title,

        @NotBlank
        String text
) {
}
