package ru.clevertec.statkevich.newsservice.testutil.builder.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@With
public class CommentVoTestBuilder implements Builder<CommentVo> {

    private Long id = 1L;

    private LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private String text = "text";
    private String username = "username";

    private Long newsId = 1L;

    public static CommentVoTestBuilder createCommentVoDto() {
        return new CommentVoTestBuilder();
    }

    @Override
    public CommentVo build() {
        return new CommentVo(id, time, text, username);
    }

}
