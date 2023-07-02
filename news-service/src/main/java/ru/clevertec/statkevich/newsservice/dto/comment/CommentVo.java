package ru.clevertec.statkevich.newsservice.dto.comment;

import java.time.LocalDateTime;

public record CommentVo(

        Long id,

        LocalDateTime time,

        String text,

        String username
) {
}
