package ru.clevertec.statkevich.newsservice.testutil.builder.news;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.news.NewsSingleVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsSingleVoTestBuilder implements Builder<NewsSingleVo> {

    private Long id = 1L;

    private LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private String title = "title";

    private String text = "text";

    private String username = "username";


    public static NewsSingleVoTestBuilder createNewsVoDto() {
        return new NewsSingleVoTestBuilder();
    }

    @Override
    public NewsSingleVo build() {
        return new NewsSingleVo(id, time, title, text, username);
    }

}
