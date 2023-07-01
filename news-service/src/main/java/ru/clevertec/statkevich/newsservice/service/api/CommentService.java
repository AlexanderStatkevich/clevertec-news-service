package ru.clevertec.statkevich.newsservice.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;

/**
 * CommentService describes methods for implementation CRUD operations for {@link ru.clevertec.statkevich.newsservice.domain.Comment}.
 */
public interface CommentService {

    Comment create(Comment comment);

    Comment findById(Long id);

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findAllByNewsId(Long id, Pageable pageable);

    Page<Comment> findAllFiltered(Filter<Comment> filter, Pageable pageable);

    Comment update(Long id, CommentUpdateDto commentUpdateDto);

    void delete(Long id);
}
