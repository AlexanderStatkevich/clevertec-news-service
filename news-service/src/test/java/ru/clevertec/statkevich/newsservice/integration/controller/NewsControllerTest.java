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
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;
import ru.clevertec.statkevich.newsservice.integration.BaseIntegrationTest;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestNewsControllerData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest extends BaseIntegrationTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    String newsMapping = "/api/v1/news";


    @Nested
    @DisplayName("Tests on find controller methods")
    class TestsOnFind {
        @Test
        void findByIdIntegrationTest() throws Exception {
            NewsVo newsVo = TestNewsControllerData.buildApiFindByIdResponse();
            mockMvc.perform(get(newsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(newsVo)));
        }

        @Test
        void findByIdWrongInputIntegrationTest() throws Exception {

            mockMvc.perform(get(newsMapping + "/{id}", 16)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void findAllIntegrationTest() throws Exception {
            Page<NewsVo> newsVos = TestNewsControllerData.buildApiFindAllResponse();

            mockMvc.perform(get(newsMapping)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(newsVos)));
        }
    }

    @Nested
    @DisplayName("Tests on update controller method")
    class TestsOnUpdate {

        @WithMockUser(username = "subscriber", authorities = "ADMIN")
        @Test
        void updateWrongUserRefuse() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            mockMvc.perform(patch(newsMapping + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }

        @WithMockUser(username = "subscriber1", authorities = "SUBSCRIBER")
        @Test
        void updateRefuseTest() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            mockMvc.perform(patch(newsMapping + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }
    }


    @Nested
    @DisplayName("Tests on create controller method")
    class TestsOnCreate {
        @WithMockUser(authorities = "ADMIN")
        @Test
        void createIntegrationTest() throws Exception {

            NewsCreateDto newsCreateDto = NewsCreateDtoTestBuilder.createNewsCreateDto().build();

            mockMvc.perform(post(newsMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsCreateDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("6"));
        }

        @WithMockUser(authorities = "JOURNALIST")
        @Test
        void wrongInputCreateIntegrationTest() throws Exception {

            NewsCreateDto newsCreateDto = NewsCreateDtoTestBuilder.createNewsCreateDto().withText(null).build();


            mockMvc.perform(post(newsMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsCreateDto)))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @DisplayName("Tests on delete controller method")
    class TestsOnDelete {
        @WithMockUser(username = "journalist1", authorities = "ADMIN")
        @Test
        void deleteByAdminTest() throws Exception {

            mockMvc.perform(delete(newsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "journalist1", authorities = "JOURNALIST")
        @Test
        void deleteByJournalistTest() throws Exception {

            mockMvc.perform(delete(newsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "journalist1", authorities = "SUBSCRIBER")
        @Test
        void deleteRefuseTest() throws Exception {

            mockMvc.perform(delete(newsMapping + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is5xxServerError());
        }
    }
}
