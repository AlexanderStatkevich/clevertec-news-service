package ru.clevertec.statkevich.newsservice.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;

/**
 * NewsService describes methods for implementation CRUD operations for {@link ru.clevertec.statkevich.newsservice.domain.News}.
 */
public interface NewsService {

    News create(News news);

    News findById(Long id);

    Page<News> findAll(Pageable pageable);

    Page<News> findAllFiltered(Filter<News> filter, Pageable pageable);

    News update(Long id, NewsUpdateDto newsUpdateDto);

    void delete(Long id);
}
