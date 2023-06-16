package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.repository.CommentRepository;
import ru.clevertec.statkevich.newsservice.service.api.CommentService;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public Long create(Comment comment) {
        return commentRepository.saveAndFlush(comment).getId();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = findById(id);
        commentMapper.map(commentUpdateDto, comment);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
