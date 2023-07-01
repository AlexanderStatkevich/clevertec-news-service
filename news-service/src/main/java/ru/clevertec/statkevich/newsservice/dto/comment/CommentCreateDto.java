package ru.clevertec.statkevich.newsservice.dto.comment;

public record CommentCreateDto(

        String text,

        Long newsId
) {
}
