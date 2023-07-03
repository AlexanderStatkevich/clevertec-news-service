package ru.clevertec.statkevich.newsservice.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateDto(

        @NotBlank
        String text

) {
}
