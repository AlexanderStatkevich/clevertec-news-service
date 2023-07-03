package ru.clevertec.statkevich.newsservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.integration.BaseIntegrationTest;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestCommentControllerData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest extends BaseIntegrationTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    String commentsMapping = "/api/v1/comments";


    @Nested
    @DisplayName("Tests on find controller methods")
    class TestsOnFind {
        @Test
        void findByIdIntegrationTest() throws Exception {
            CommentVo commentVo = TestCommentControllerData.buildApiFindByIdResponse();
            mockMvc.perform(get(commentsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(commentVo)));
        }

        @Test
        void findByIdWrongInputIntegrationTest() throws Exception {

            mockMvc.perform(get(commentsMapping + "/{id}", 16)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void findAllIntegrationTest() throws Exception {
            Page<CommentVo> commentVos = TestCommentControllerData.buildApiFindAllResponse();

            mockMvc.perform(get(commentsMapping)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(commentVos)));
        }
    }

    @Nested
    @DisplayName("Tests on update controller method")
    class TestsOnUpdate {

        @WithMockUser(username = "subscriber", authorities = "ADMIN")
        @Test
        void updateWrongUserRefuse() throws Exception {

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            mockMvc.perform(patch(commentsMapping + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }

        @WithMockUser(username = "subscriber1", authorities = "JOURNALIST")
        @Test
        void updateRefuseTest() throws Exception {

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            mockMvc.perform(patch(commentsMapping + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }
    }


    @Nested
    @DisplayName("Tests on create controller method")
    class TestsOnCreate {
        @WithMockUser(authorities = "ADMIN")
        @Test
        void createIntegrationTest() throws Exception {

            CommentCreateDto commentCreateDto = CommentCreateDtoTestBuilder.createCommentCreateDto().build();

            mockMvc.perform(post(commentsMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentCreateDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("16"));
        }

        @WithMockUser(authorities = "SUBSCRIBER")
        @Test
        void wrongInputCreateIntegrationTest() throws Exception {

            CommentCreateDto commentCreateDto = CommentCreateDtoTestBuilder.createCommentCreateDto().withNewsId(null).build();

            mockMvc.perform(post(commentsMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentCreateDto)))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @DisplayName("Tests on delete controller method")
    class TestsOnDelete {
        @WithMockUser(username = "subscriber1", authorities = "ADMIN")
        @Test
        void deleteByAdminTest() throws Exception {

            mockMvc.perform(delete(commentsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "subscriber1", authorities = "SUBSCRIBER")
        @Test
        void deleteBySubscriberTest() throws Exception {

            mockMvc.perform(delete(commentsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "subscriber1", authorities = "JOURNALIST")
        @Test
        void deleteRefuseTest() throws Exception {

            mockMvc.perform(delete(commentsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is5xxServerError());
        }
    }
}

