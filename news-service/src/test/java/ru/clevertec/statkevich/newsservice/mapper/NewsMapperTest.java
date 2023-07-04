package ru.clevertec.statkevich.newsservice.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.clevertec.statkevich.newsservice.BaseTest;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsSingleVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtUserDetails;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsSingleVoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsUpdateVoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsVoTestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class NewsMapperTest extends BaseTest {

    private final NewsMapper newsMapper;

    @Test
    void checkToVoReturnCorrectValue() {
        News source = NewsTestBuilder.createNews().build();
        NewsVo expected = NewsVoTestBuilder.createNewsVoDto().build();
        NewsVo actual = newsMapper.toVo(source);

        assertEquals(expected, actual);
    }

    @Test
    void checkToUpdateVoReturnCorrectValue() {
        News source = NewsTestBuilder.createNews().build();
        NewsUpdateVo expected = NewsUpdateVoTestBuilder.createNewsVoDto().build();
        NewsUpdateVo actual = newsMapper.toUpdateVo(source);

        assertEquals(expected, actual);

    }

    @Test
    void checkSingleVoReturnCorrectValue() {
        News source = NewsTestBuilder.createNews().build();
        NewsSingleVo expected = NewsSingleVoTestBuilder.createNewsVoDto().build();
        NewsSingleVo actual = newsMapper.toSingleVo(source);

        assertEquals(expected, actual);
    }

    @Test
    void checkToVoWithPageReturnCorrectValue() {
        News newsSource = NewsTestBuilder.createNews().build();
        NewsVo expected = NewsVoTestBuilder.createNewsVoDto().withCommentVos(Page.empty()).build();
        NewsVo actual = newsMapper.toVo(newsSource, Page.empty());

        assertEquals(expected, actual);
    }

    @Test
    void checkToEntityReturnCorrectValue() {

        NewsCreateDto source = NewsCreateDtoTestBuilder.createNewsCreateDto().build();
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        News actual = newsMapper.toEntity(source);
        News expected = NewsTestBuilder.createNews().withId(null).withTime(null).build();

        assertThat(expected).isEqualTo(actual);
    }


    @Test
    void checkMapProceedCorrectMapping() {
        NewsUpdateDto source = NewsUpdateDtoTestBuilder.createNewsUpdateDto().withText("mapped").build();
        News target = NewsTestBuilder.createNews().build();
        newsMapper.map(source, target);
        News expected = NewsTestBuilder.createNews().withText("mapped").build();
        assertEquals(expected, target);
    }
}
