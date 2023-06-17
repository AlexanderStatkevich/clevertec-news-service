package ru.clevertec.statkevich.newsservice.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;

public interface NewsService {

    Long create(News news);

    News findById(Long id);

    Page<News> findAll(Filter<News> filter, Pageable pageable);

    News update(Long id, NewsUpdateDto newsUpdateDto);

    void delete(Long id);
}
