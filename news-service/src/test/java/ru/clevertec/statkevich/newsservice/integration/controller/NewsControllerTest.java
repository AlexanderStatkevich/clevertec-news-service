package ru.clevertec.statkevich.newsservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.statkevich.newsservice.BaseTest;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.news.NewsUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestNewsControllerJsonData;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestSecurityJsonData;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.NEWS_MAPPING;
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.TOKEN;
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.USERS_CLIENT_MAPPING;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest extends BaseTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;


    @Nested
    @DisplayName("Tests on find controller methods")
    class TestsOnFind {
        @Test
        void findByIdIntegrationTest() throws Exception {
            String newsVo = TestNewsControllerJsonData.readApiFindByIdResponse();
            mockMvc.perform(get(NEWS_MAPPING + "/{id}", 1)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(newsVo));
        }

        @Test
        void findByIdWrongInputIntegrationTest() throws Exception {

            mockMvc.perform(get(NEWS_MAPPING + "/{id}", 16)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void findAllIntegrationTest() throws Exception {
            String newsVos = TestNewsControllerJsonData.readApiFindAllResponse();

            mockMvc.perform(get(NEWS_MAPPING)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(newsVos));
        }
    }

    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on update controller method")
    class TestsOnUpdate {

        @WithMockUser(username = "journalist1", authorities = "JOURNALIST")
        @Test
        void updateIntegrationTest() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            String response = TestNewsControllerJsonData.readUpdateResponse();

            mockMvc.perform(patch(NEWS_MAPPING + "/{id}", 2)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(response));
        }

        @Test
        void updateWithMockCallSideServiceIntegrationTest() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            String response = TestNewsControllerJsonData.readUpdateResponse();
            String userAuthorityDto = TestSecurityJsonData.readNewsUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));


            mockMvc.perform(patch(NEWS_MAPPING + "/{id}", 2)
                            .contentType(APPLICATION_JSON)
                            .header("authorization", "Bearer " + TOKEN)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(response));
        }


        @WithMockUser(username = "subscriber", authorities = "ADMIN")
        @Test
        void updateWrongUserRefuse() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            mockMvc.perform(patch(NEWS_MAPPING + "/{id}", 1)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }

        @WithMockUser(username = "subscriber1", authorities = "SUBSCRIBER")
        @Test
        void updateRefuseTest() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().build();
            mockMvc.perform(patch(NEWS_MAPPING + "/{id}", 1)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }

        @WithMockUser(username = "subscriber1", authorities = "SUBSCRIBER")
        @Test
        void updatePassWrongValueExpect4xx() throws Exception {

            NewsUpdateDto newsUpdateDto = NewsUpdateDtoTestBuilder.createNewsUpdateDto().withText(null).build();
            mockMvc.perform(patch(NEWS_MAPPING + "/{id}", 1)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsUpdateDto)))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on create controller method")
    class TestsOnCreate {
        @WithMockUser(authorities = "ADMIN")
        @Test
        void createIntegrationTest() throws Exception {

            NewsCreateDto newsCreateDto = NewsCreateDtoTestBuilder.createNewsCreateDto().build();

            mockMvc.perform(post(NEWS_MAPPING)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsCreateDto)))
                    .andExpect(status().isCreated());
        }

        @Test
        void createWithMockCallSideServiceIntegrationTest() throws Exception {

            String userAuthorityDto = TestSecurityJsonData.readNewsUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));

            NewsCreateDto newsCreateDto = NewsCreateDtoTestBuilder.createNewsCreateDto().build();

            mockMvc.perform(post(NEWS_MAPPING)
                            .contentType(APPLICATION_JSON)
                            .header("authorization", "Bearer " + TOKEN)
                            .content(objectMapper.writeValueAsString(newsCreateDto)))
                    .andExpect(status().isCreated());
        }

        @WithMockUser(authorities = "JOURNALIST")
        @Test
        void wrongInputCreateIntegrationTest() throws Exception {

            NewsCreateDto newsCreateDto = NewsCreateDtoTestBuilder.createNewsCreateDto().withText(null).build();


            mockMvc.perform(post(NEWS_MAPPING)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newsCreateDto)))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on delete controller method")
    class TestsOnDelete {
        @WithMockUser(username = "journalist1", authorities = "ADMIN")
        @Test
        void deleteByAdminTest() throws Exception {

            mockMvc.perform(delete(NEWS_MAPPING + "/{id}", 1)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteWithMockCallSideServiceByAdminTest() throws Exception {
            String userAuthorityDto = TestSecurityJsonData.readNewsUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));

            mockMvc.perform(delete(NEWS_MAPPING + "/{id}", 2)
                            .header("authorization", "Bearer " + TOKEN)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "journalist1", authorities = "JOURNALIST")
        @Test
        void deleteByJournalistTest() throws Exception {

            mockMvc.perform(delete(NEWS_MAPPING + "/{id}", 1)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "journalist1", authorities = "SUBSCRIBER")
        @Test
        void deleteRefuseTest() throws Exception {

            mockMvc.perform(delete(NEWS_MAPPING + "/{id}", 1)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().is5xxServerError());
        }
    }
}
