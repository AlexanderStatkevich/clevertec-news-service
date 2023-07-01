package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
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
    public News create(News news) {
        newsRepository.saveAndFlush(news);
        return news;
    }


    @Cacheable("news")
    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News with id " + id + " not found"));
    }


    @Cacheable(value = "news", key = "#pageable.pageSize+#pageable.pageNumber")
    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Cacheable(value = "news", key = "#pageable.pageSize+#pageable.pageNumber")
    @Override
    public Page<News> findAllFiltered(Filter<News> filter, Pageable pageable) {
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
