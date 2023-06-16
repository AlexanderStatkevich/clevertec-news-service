package ru.clevertec.statkevich.newsservice.dto;

public record CommentCreateDto(

        String text,

        String username,

        Long newsId
) {
}
