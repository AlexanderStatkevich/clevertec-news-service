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
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.filter.Comparison;
import ru.clevertec.statkevich.newsservice.filter.Condition;
import ru.clevertec.statkevich.newsservice.filter.Filter;
import ru.clevertec.statkevich.newsservice.mapper.NewsMapper;
import ru.clevertec.statkevich.newsservice.repository.NewsRepository;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtUserDetails;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsUpdateDtoTestBuilder;

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
class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Captor
    private ArgumentCaptor<News> argumentCaptor;

    @Test
    void checkFindAllReturnCorrectValue() {
        Pageable pageable = Pageable.ofSize(20);
        News news = NewsTestBuilder.createNews().build();
        PageImpl<News> expected = new PageImpl<>(List.of(news), pageable, 1L);
        when(newsRepository.findAll(pageable)).thenReturn(expected);
        Page<News> actual = newsService.findAll(pageable);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindAllFilteredReturnCorrectValue() {
        Pageable pageable = Pageable.ofSize(20);
        Condition condition = new Condition(Comparison.LIKE, "text", "text");
        Filter<News> filter = new Filter<>();
        filter.setConditions(List.of(condition));
        News news = NewsTestBuilder.createNews().build();
        PageImpl<News> expected = new PageImpl<>(List.of(news), pageable, 1L);
        when(newsRepository.findAll(filter, pageable)).thenReturn(expected);
        Page<News> actual = newsService.findAllFiltered(filter, pageable);

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class CreateTest {
        @Test
        void checkCreateReturnCorrectValue() {
            News news = NewsTestBuilder.createNews().build();
            when(newsRepository.saveAndFlush(news)).thenReturn(news);
            Long expected = newsService.create(news).getId();
            assertThat(1L).isEqualTo(expected);
        }

        @Test
        void checkCreateCallCorrectRepositoryMethod() {
            News news = NewsTestBuilder.createNews().build();
            newsService.create(news);
            verify(newsRepository).saveAndFlush(argumentCaptor.capture());
            News captorValue = argumentCaptor.getValue();
            assertThat(captorValue).isEqualTo(news);
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        void checkFindByIdReturnCorrectValue() {
            News expected = NewsTestBuilder.createNews().build();
            when(newsRepository.findById(1L)).thenReturn(Optional.ofNullable(expected));
            News actual = newsService.findById(1L);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void checkFindByIdThrowEntityNotFoundException() {
            long notExistingId = 1L;
            doThrow(new EntityNotFoundException("News with id " + notExistingId + " not found"))
                    .when(newsRepository)
                    .findById(notExistingId);

            assertThrows(EntityNotFoundException.class, () -> newsService.findById(notExistingId));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateSucceed() {
            long id = 1L;
            News news = NewsTestBuilder.createNews().build();
            when(newsRepository.findById(id)).thenReturn(Optional.ofNullable(news));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().withText("updated").build();

            News expected = NewsTestBuilder.createNews().withText("updated").build();

            doAnswer((comm) -> {
                assert news != null;
                news.setText("updated");
                return null;
            }).when(newsMapper).map(newsUpdateDto, news);

            News actual = newsService.update(id, newsUpdateDto);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void checkUpdateThrowEntityNotFoundException() {
            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().withText("updated").build();
            long id = 1L;
            assertThrows(EntityNotFoundException.class, () -> newsService.update(id, newsUpdateDto));
        }

        @Test
        void checkUpdateThrowAccessDeniedException() {
            long id = 1L;
            News news = NewsTestBuilder.createNews().build();
            when(newsRepository.findById(id)).thenReturn(Optional.ofNullable(news));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("not_username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);
            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().withText("updated").build();

            assertThrows(AccessDeniedException.class, () -> newsService.update(id, newsUpdateDto));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        void checkDeleteSucceed() {
            long id = 1L;
            News news = NewsTestBuilder.createNews().build();
            when(newsRepository.findById(id)).thenReturn(Optional.ofNullable(news));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);
            newsService.delete(id);
            verify(newsRepository, times(1)).deleteById(id);
        }

        @Test
        void checkDeleteThrowEntityNotFoundException() {
            long id = 1L;
            assertThrows(EntityNotFoundException.class, () -> newsService.delete(id));
        }

        @Test
        void checkDeleteThrowAccessDeniedException() {
            long id = 1L;
            News news = NewsTestBuilder.createNews().build();
            when(newsRepository.findById(id)).thenReturn(Optional.ofNullable(news));

            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            UserDetails userDetails = new JwtUserDetails("not_username", "ADMIN");
            when(authentication.getPrincipal()).thenReturn(userDetails);

            assertThrows(AccessDeniedException.class, () -> newsService.delete(id));
        }
    }
}
