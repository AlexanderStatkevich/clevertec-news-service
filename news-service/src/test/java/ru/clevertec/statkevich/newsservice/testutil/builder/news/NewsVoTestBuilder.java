package ru.clevertec.statkevich.newsservice.testutil.builder.news;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Page;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsVoTestBuilder implements Builder<NewsVo> {

    private Long id = 1L;

    private LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private String title = "title";

    private String text = "text";

    private String username = "username";

    private Page<CommentVo> commentVos;

    public static NewsVoTestBuilder createNewsVoDto() {
        return new NewsVoTestBuilder();
    }

    @Override
    public NewsVo build() {
        return new NewsVo(id, time, title, text, username, commentVos);
    }

}
