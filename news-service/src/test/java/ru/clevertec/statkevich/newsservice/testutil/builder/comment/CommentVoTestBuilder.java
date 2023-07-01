package ru.clevertec.statkevich.newsservice.testutil.builder.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@With
public class CommentVoTestBuilder implements Builder<CommentVo> {

    private Long id;

    private LocalDateTime time;
    private String text;
    private Long newsId;

    public static CommentVoTestBuilder createCommentVoDto() {
        return new CommentVoTestBuilder();
    }

    @Override
    public CommentVo build() {
        return new CommentVo(id, time, text, newsId);
    }

}
