package ru.clevertec.statkevich.newsservice.dto;

import java.time.LocalDateTime;

public record NewsVo(

        Long id,

        LocalDateTime time,

        String title,

        String text
) {
}
