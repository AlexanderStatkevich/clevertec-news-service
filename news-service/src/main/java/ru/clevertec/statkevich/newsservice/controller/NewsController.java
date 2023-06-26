package ru.clevertec.statkevich.newsservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.NewsVo;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.NewsMapper;
import ru.clevertec.statkevich.newsservice.service.api.NewsService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/news")
public class NewsController {

    private final NewsService newsService;

    private final NewsMapper newsMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> create(@Valid @RequestBody NewsCreateDto newsCreateDto) {
        News news = newsMapper.toEntity(newsCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(news));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsVo> findById(@PathVariable Long id) {
        News news = newsService.findById(id);
        return ResponseEntity.ok(newsMapper.toDto(news));
    }

    @GetMapping
    public ResponseEntity<Page<NewsVo>> findAll(@RequestBody Filter<News> filter, Pageable pageable) {
        Page<News> newsPage = newsService.findAll(filter, pageable);
        return ResponseEntity.ok(newsPage.map(newsMapper::toDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<NewsVo> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody NewsUpdateDto newsUpdateDto) {
        News updatedNews = newsService.update(id, newsUpdateDto);
        return ResponseEntity.ok(newsMapper.toDto(updatedNews));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        newsService.delete(id);
    }
}
