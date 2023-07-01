package ru.clevertec.statkevich.newsservice.testutil.builder.news;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@With

public class NewsTestBuilder implements Builder<News> {

    private Long id = 1L;
    private LocalDateTime time = LocalDateTime.now();
    private String text = "text";

    private String title = "title";

    private List<Comment> comments = List.of(new Comment());

    public static NewsTestBuilder createNews() {
        return new NewsTestBuilder();
    }

    @Override
    public News build() {
        return News.builder().id(id).time(time).text(text).title(title).comments(comments).build();
    }
}
