package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.NewsMapper;
import ru.clevertec.statkevich.newsservice.repository.NewsRepository;
import ru.clevertec.statkevich.newsservice.service.api.NewsService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Transactional
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

    @Transactional
    @Override
    public News update(Long id, NewsUpdateDto newsUpdateDto) {
        News news = findById(id);
        if (isNewsOwner(news)) {
            newsMapper.map(newsUpdateDto, news);
            newsRepository.save(news);
            return news;
        }
        throw new AccessDeniedException("modifying prohibited");
    }

    @Transactional
    @Override
    public void delete(Long id) {
        News news = findById(id);
        if (isNewsOwner(news)) {
            newsRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("modifying prohibited");
        }
    }

    private boolean isNewsOwner(News news) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernamePrincipal = principal.getUsername();
        String username = news.getUsername();

        return username.equals(usernamePrincipal);
    }
}
