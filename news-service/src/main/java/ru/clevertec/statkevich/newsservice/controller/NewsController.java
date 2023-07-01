package ru.clevertec.statkevich.newsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.mapper.NewsMapper;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;
import ru.clevertec.statkevich.newsservice.service.api.NewsService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/news")
public class NewsController {

    public static final int PAGE_SIZE = 20;
    private final NewsService newsService;

    private final CommentService commentService;
    private final NewsMapper newsMapper;

    private final CommentMapper commentMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> create(@Valid @RequestBody NewsCreateDto newsCreateDto) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        News news = newsMapper.toEntity(newsCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(news).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsVo> findById(@PathVariable Long id) {
        News news = newsService.findById(id);
        Page<Comment> comments = commentService.findAllByNewsId(id, Pageable.ofSize(PAGE_SIZE));
        Page<CommentVo> commentVos = comments.map(commentMapper::toVo);
        NewsVo newsVo = newsMapper.toVo(news, commentVos);
        return ResponseEntity.ok(newsVo);
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<Page<NewsVo>> findAll(Pageable pageable) {
        Page<News> newsPage = newsService.findAll(pageable);
        return ResponseEntity.ok(newsPage.map(newsMapper::toVo));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<Page<NewsVo>> findAllFiltered(@RequestParam(name = "filter") String filter, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Filter<News> filterObject = objectMapper.readValue(filter, Filter.class);
        Page<News> newsPage = newsService.findAllFiltered(filterObject, pageable);
        return ResponseEntity.ok(newsPage.map(newsMapper::toVo));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<NewsVo> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody NewsUpdateDto newsUpdateDto) {
        News updatedNews = newsService.update(id, newsUpdateDto);
        return ResponseEntity.ok(newsMapper.toVo(updatedNews));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        newsService.delete(id);
    }
}
