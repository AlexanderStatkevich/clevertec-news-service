package ru.clevertec.statkevich.newsservice.dto.news;

import jakarta.validation.constraints.NotBlank;

public record NewsUpdateDto(

        @NotBlank
        String text,

        @NotBlank
        String title

) {
}
