package ru.clevertec.statkevich.newsservice.testutil.builder.news;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsUpdateDtoTestBuilder implements Builder<NewsUpdateDto> {

    private String text = "text";

    private String title = "title";


    public static NewsUpdateDtoTestBuilder createNewsUpdateDto() {
        return new NewsUpdateDtoTestBuilder();
    }

    @Override
    public NewsUpdateDto build() {
        return new NewsUpdateDto(text, title);
    }
}
