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
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.CommentVo;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/comments")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> create(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        Comment comment = commentMapper.toEntity(commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(comment));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentVo> findById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }
    @GetMapping
    public ResponseEntity<Page<CommentVo>> findAll(Pageable pageable) {
        Page<Comment> certificatePage = commentService.findAll(pageable);
        return ResponseEntity.ok(certificatePage.map(commentMapper::toDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CommentVo> update(@PathVariable("id") Long id,
                                            @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        Comment updatedComment = commentService.update(id, commentUpdateDto);
        return ResponseEntity.ok(commentMapper.toDto(updatedComment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
