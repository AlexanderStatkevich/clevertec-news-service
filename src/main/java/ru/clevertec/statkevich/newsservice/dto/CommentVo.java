package ru.clevertec.statkevich.newsservice.dto;

import java.time.LocalDateTime;

public record CommentVo(

        Long id,

        LocalDateTime time,

        String text,

        Long newsId
) {
}
