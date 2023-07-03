package ru.clevertec.statkevich.newsservice.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.integration.BaseIntegrationTest;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtUserDetails;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentVoTestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CommentMapperTest extends BaseIntegrationTest {

    private final CommentMapper commentMapper;

    @Test
    void checkToVoReturnCorrectValue() {
        Comment source = CommentTestBuilder.createComment().build();
        CommentVo expected = CommentVoTestBuilder.createCommentVoDto().build();
        CommentVo actual = commentMapper.toVo(source);
        assertEquals(expected, actual);
    }

    @Test
    void checkToEntityReturnCorrectValue() {
        CommentCreateDto source = CommentCreateDtoTestBuilder.createCommentCreateDto().build();
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = new JwtUserDetails("username", "ADMIN");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        Comment actual = commentMapper.toEntity(source);
        Comment expected = CommentTestBuilder.createComment().withId(null).withTime(null).build();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void checkMapProceedCorrectMapping() {
        CommentUpdateDto source = CommentUpdateDtoTestBuilder.createCommentUpdateDto().withText("mapped").build();
        Comment target = CommentTestBuilder.createComment().build();
        commentMapper.map(source, target);
        Comment expected = CommentTestBuilder.createComment().withText("mapped").build();
        assertEquals(expected, target);
    }
}
