package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.NewsMapper;
import ru.clevertec.statkevich.newsservice.repository.NewsRepository;
import ru.clevertec.statkevich.newsservice.service.api.NewsService;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Override
    public Long create(News news) {
        newsRepository.saveAndFlush(news);
        return news.getId();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News with id " + id + " not found"));
    }

    @Override
    public Page<News> findAll(Filter<News> filter, Pageable pageable) {
        return newsRepository.findAll(filter, pageable);
    }

    @Override
    public News update(Long id, NewsUpdateDto newsUpdateDto) {

        News news = findById(id);
        newsMapper.map(newsUpdateDto, news);
        newsRepository.save(news);
        return news;
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}
