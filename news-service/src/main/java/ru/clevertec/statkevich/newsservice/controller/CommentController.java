package ru.clevertec.statkevich.newsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> create(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        Comment comment = commentMapper.toEntity(commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(comment).getId());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentVo> findById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.ok(commentMapper.toVo(comment));
    }


    @GetMapping
    public ResponseEntity<Page<CommentVo>> findAll(Pageable pageable) {
        Page<Comment> commentPage = commentService.findAll(pageable);
        return ResponseEntity.ok(commentPage.map(commentMapper::toVo));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<Page<CommentVo>> findAllFiltered(@RequestParam(name = "filter") String filter, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Filter<Comment> filterObject = objectMapper.readValue(filter, Filter.class);
        Page<Comment> commentPage = commentService.findAllFiltered(filterObject, pageable);
        return ResponseEntity.ok(commentPage.map(commentMapper::toVo));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CommentVo> update(@PathVariable("id") Long id,
                                            @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        Comment updatedComment = commentService.update(id, commentUpdateDto);
        return ResponseEntity.ok(commentMapper.toVo(updatedComment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
