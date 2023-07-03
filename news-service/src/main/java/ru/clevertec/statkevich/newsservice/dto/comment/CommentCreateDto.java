package ru.clevertec.statkevich.newsservice.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateDto(

        @NotBlank
        String text,
        @NotNull
        Long newsId
) {
}
