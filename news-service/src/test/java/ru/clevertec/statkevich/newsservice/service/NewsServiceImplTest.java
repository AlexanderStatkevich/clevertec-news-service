package ru.clevertec.statkevich.newsservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.repository.NewsRepository;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    void create() {
        News news = NewsTestBuilder.createNews().build();
        when(newsRepository.saveAndFlush(news)).thenReturn(news);
        Long expected = newsService.create(news).getId();
        assertThat(1L).isEqualTo(expected);
    }

    @Test
    void findById() {
        News expected = NewsTestBuilder.createNews().build();
        when(newsRepository.findById(1L)).thenReturn(Optional.ofNullable(expected));
        News actual = newsService.findById(1L);
        assertThat(actual).isEqualTo(expected);
    }

}
