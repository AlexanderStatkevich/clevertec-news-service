package ru.clevertec.statkevich.newsservice.testutil.builder.news;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsUpdateVoTestBuilder implements Builder<NewsUpdateVo> {

    private Long id = 1L;

    private LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private String title = "title";

    private String text = "text";

    private String username = "username";


    public static NewsUpdateVoTestBuilder createNewsVoDto() {
        return new NewsUpdateVoTestBuilder();
    }

    @Override
    public NewsUpdateVo build() {
        return new NewsUpdateVo(id, time, title, text, username);
    }

}
