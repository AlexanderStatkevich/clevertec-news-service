package ru.clevertec.statkevich.newsservice.testutil.builder.news;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Page;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsVoTestBuilder implements Builder<NewsVo> {

    private Long id;

    private LocalDateTime time;

    private String title;

    private String text;

    private String username;

    public static NewsVoTestBuilder createNewsVoDto() {
        return new NewsVoTestBuilder();
    }

    @Override
    public NewsVo build() {
        return new NewsVo(id, time, title, text, username, Page.empty());
    }

}
