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
import ru.clevertec.statkevich.newsservice.dto.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.repository.CommentRepository;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;

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
    public Long create(Comment comment) {
        return commentRepository.saveAndFlush(comment).getId();
    }

    @Override
    @Cacheable("comment")
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
    }


    @Override
    @Cacheable(value = "comments", key = "#pageable.pageSize+#pageable.pageNumber")
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @CachePut("comment")
//    @CacheEvict(value = "comments", allEntries = true)
    public Comment update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = findById(id);
        commentMapper.map(commentUpdateDto, comment);
        commentRepository.save(comment);
        return comment;
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict("comment"),
            @CacheEvict(value = "comments", allEntries = true, key = "#p0")
    })
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
