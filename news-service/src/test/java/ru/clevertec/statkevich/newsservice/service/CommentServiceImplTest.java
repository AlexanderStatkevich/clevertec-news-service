package ru.clevertec.statkevich.newsservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Comparison;
import ru.clevertec.statkevich.newsservice.filter.Condition;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.CommentMapper;
import ru.clevertec.statkevich.newsservice.repository.CommentRepository;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtUserDetails;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentUpdateDtoTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Captor
    private ArgumentCaptor<Comment> argumentCaptor;

    @Test
    void checkFindAllReturnCorrectValue() {
        Pageable pageable = Pageable.ofSize(20);
        Comment comment = CommentTestBuilder.createComment().build();
        PageImpl<Comment> expected = new PageImpl<>(List.of(comment), pageable, 1L);
        when(commentRepository.findAll(pageable)).thenReturn(expected);
        Page<Comment> actual = commentService.findAll(pageable);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindAllFilteredReturnCorrectValue() {
        Pageable pageable = Pageable.ofSize(20);
        Condition condition = new Condition(Comparison.LIKE, "text", "text");
        Filter<Comment> filter = new Filter<>();
        filter.setConditions(List.of(condition));
        Comment comment = CommentTestBuilder.createComment().build();
        PageImpl<Comment> expected = new PageImpl<>(List.of(comment), pageable, 1L);
        when(commentRepository.findAll(filter, pageable)).thenReturn(expected);
        Page<Comment> actual = commentService.findAllFiltered(filter, pageable);

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class CreateTest {
        @Test
        void checkCreateReturnCorrectValue() {
            Comment comment = CommentTestBuilder.createComment().build();
            when(commentRepository.saveAndFlush(comment)).thenReturn(comment);
            Long expected = commentService.create(comment).getId();
            assertThat(1L).isEqualTo(expected);
        }

        @Test
        void checkCreateCallCorrectRepositoryMethod() {
            Comment comment = CommentTestBuilder.createComment().build();
            commentService.create(comment);
            verify(commentRepository).saveAndFlush(argumentCaptor.capture());
            Comment captorValue = argumentCaptor.getValue();
            assertThat(captorValue).isEqualTo(comment);
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        void checkFindByIdReturnCorrectValue() {
            Comment expected = CommentTestBuilder.createComment().build();
            when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(expected));
            Comment actual = commentService.findById(1L);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void checkFindByIdThrowEntityNotFoundException() {
            long notExistingId = 1L;
            doThrow(new EntityNotFoundException("Comment with id " + notExistingId + " not found"))
                    .when(commentRepository)
                    .findById(notExistingId);

            assertThrows(EntityNotFoundException.class, () -> commentService.findById(notExistingId));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateSucceed() {
            long id = 1L;
            Comment comment = CommentTestBuilder.createComment().build();
            when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(comment));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().withText("updated").build();
            Comment expected = CommentTestBuilder.createComment().withText("updated").build();

            doAnswer((comm) -> {
                assert comment != null;
                comment.setText("updated");
                return null;
            }).when(commentMapper).map(commentUpdateDto, comment);

            Comment actual = commentService.update(id, commentUpdateDto);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void checkUpdateThrowEntityNotFoundException() {
            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().withText("updated").build();
            long id = 1L;
            assertThrows(EntityNotFoundException.class, () -> commentService.update(id, commentUpdateDto));
        }

        @Test
        void checkUpdateThrowAccessDeniedException() {
            long id = 1L;
            Comment comment = CommentTestBuilder.createComment().build();
            when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(comment));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("not_username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);
            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().withText("updated").build();
            assertThrows(AccessDeniedException.class, () -> commentService.update(id, commentUpdateDto));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        void checkDeleteSucceed() {
            long id = 1L;
            Comment comment = CommentTestBuilder.createComment().build();
            when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(comment));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);
            commentService.delete(id);
            verify(commentRepository, times(1)).deleteById(id);
        }

        @Test
        void checkDeleteThrowEntityNotFoundException() {
            long id = 1L;
            assertThrows(EntityNotFoundException.class, () -> commentService.delete(id));
        }

        @Test
        void checkDeleteThrowAccessDeniedException() {
            long id = 1L;
            Comment comment = CommentTestBuilder.createComment().build();
            when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(comment));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("not_username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);

            assertThrows(AccessDeniedException.class, () -> commentService.delete(id));
        }
    }
}
