package ru.clevertec.statkevich.newsservice.dto.news;

import java.time.LocalDateTime;

public record NewsSingleVo(

        Long id,

        LocalDateTime time,

        String title,

        String text,

        String username
) {
}
