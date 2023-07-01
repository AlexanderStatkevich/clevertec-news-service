package ru.clevertec.statkevich.newsservice.testutil.builder.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@With
public class CommentTestBuilder implements Builder<Comment> {

    private Long id = 1L;
    private LocalDateTime time = LocalDateTime.now();
    private String text = "text";
    private String username = "username";
    private News news = new News();

    public static CommentTestBuilder createComment() {
        return new CommentTestBuilder();
    }

    @Override
    public Comment build() {
        return Comment.builder().id(id).time(time).text(text).username(username).news(news).build();
    }

}
