package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.repository.CommentRepository;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;


/**
 * Described class bind storage and output of application.
 * As well as uses CRUD operations for this purpose.
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    @CachePut("comment")
    @CacheEvict(value = "comments", allEntries = true)
    public Comment create(Comment comment) {
        commentRepository.saveAndFlush(comment);
        return comment;
    }

    @Override
    @Cacheable("comment")
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
    }


    @Override
    @Cacheable(value = "comments", key = "'size' + #pageable.pageSize + 'pageNumber' + #pageable.pageNumber")
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> findAllByNewsId(Long id, Pageable pageable) {
        return commentRepository.findAllByNewsId(id, pageable);
    }

    @Override
    @Cacheable(value = "comments", key = "'size' + #pageable.pageSize + 'pageNumber' + #pageable.pageNumber")
    public Page<Comment> findAllFiltered(Filter<Comment> filter, Pageable pageable) {
        return commentRepository.findAll(filter, pageable);
    }

    @Override
    @Transactional
    @CachePut(value = "comment", key = "#id")
    @CacheEvict(value = "comments", allEntries = true)
    public Comment update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = findById(id);
        commentMapper.map(commentUpdateDto, comment);
        commentRepository.save(comment);
        return comment;
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "comment", key = "#id"),
            @CacheEvict(value = "comments", allEntries = true)
    })
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
