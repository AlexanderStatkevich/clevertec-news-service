package ru.clevertec.statkevich.newsservice.testutil.builder.news;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

@NoArgsConstructor
@AllArgsConstructor
@With
public class NewsCreateDtoTestBuilder implements Builder<NewsCreateDto> {

    private String title = "title";

    private String text = "text";

    public static NewsCreateDtoTestBuilder createNewsCreateDto() {
        return new NewsCreateDtoTestBuilder();
    }

    @Override
    public NewsCreateDto build() {
        return new NewsCreateDto(title, text);
    }

}
