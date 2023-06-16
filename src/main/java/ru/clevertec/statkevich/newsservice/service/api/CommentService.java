package ru.clevertec.statkevich.newsservice.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.CommentUpdateDto;

public interface CommentService {

    Long create(Comment comment);

    Comment findById(Long id);

    Page<Comment> findAll(Pageable pageable);

    Comment update(Long id, CommentUpdateDto commentUpdateDto);

    void delete(Long id);
}
