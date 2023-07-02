package ru.clevertec.statkevich.newsservice.dto.news;

import org.springframework.data.domain.Page;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;

import java.time.LocalDateTime;

public record NewsVo(

        Long id,

        LocalDateTime time,

        String title,

        String text,

        String username,

        Page<CommentVo> commentVos
) {
}
