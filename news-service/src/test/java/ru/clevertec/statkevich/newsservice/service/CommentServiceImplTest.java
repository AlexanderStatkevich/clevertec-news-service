package ru.clevertec.statkevich.newsservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.repository.CommentRepository;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void create() {
        Comment comment = CommentTestBuilder.createComment().build();
        when(commentRepository.saveAndFlush(comment)).thenReturn(comment);
        Long expected = commentService.create(comment).getId();
        assertThat(1L).isEqualTo(expected);
    }

    @Test
    void findById() {
        Comment expected = CommentTestBuilder.createComment().build();
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(expected));
        Comment actual = commentService.findById(1L);
        assertThat(actual).isEqualTo(expected);
    }
}
