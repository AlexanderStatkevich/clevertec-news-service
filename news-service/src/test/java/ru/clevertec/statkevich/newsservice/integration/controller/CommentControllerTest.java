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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.statkevich.newsservice.BaseTest;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentCreateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.builder.comment.CommentUpdateDtoTestBuilder;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestCommentControllerJsonData;
import ru.clevertec.statkevich.newsservice.testutil.responsedata.TestCommentControllerObjectData;
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
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.COMMENTS_MAPPING;
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.TOKEN;
import static ru.clevertec.statkevich.newsservice.testutil.testdata.TestData.USERS_CLIENT_MAPPING;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc
public class CommentControllerTest extends BaseTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;



    @Nested
    @DisplayName("Tests on find controller methods")
    class TestsOnFind {

        @Test
        void findByIdIntegrationTest() throws Exception {
            String commentVo = TestCommentControllerJsonData.readApiFindByIdResponse();
            mockMvc.perform(get(COMMENTS_MAPPING + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(commentVo));
        }

        @Test
        void findByIdWrongInputIntegrationTest() throws Exception {

            mockMvc.perform(get(COMMENTS_MAPPING + "/{id}", 16)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void findAllIntegrationTest() throws Exception {
            Page<CommentVo> commentVos = TestCommentControllerObjectData.buildApiFindAllResponse();

            mockMvc.perform(get(COMMENTS_MAPPING)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(commentVos)));
        }
    }

    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on update controller method")
    class TestsOnUpdate {

        @WithMockUser(username = "subscriber2", authorities = "ADMIN")
        @Test
        void updateIntegrationTest() throws Exception {

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            String commentVo = TestCommentControllerJsonData.readApiUpdateResponse();

            mockMvc.perform(patch(COMMENTS_MAPPING + "/{id}", 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(commentVo));
        }

        @Test
        void updateWithMockCallSideServiceIntegrationTest() throws Exception {
            String userAuthorityDto = TestSecurityJsonData.readCommentUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            String commentVo = TestCommentControllerJsonData.readApiUpdateResponse();

            mockMvc.perform(patch(COMMENTS_MAPPING + "/{id}", 2)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("authorization", "Bearer " + TOKEN)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(commentVo));
        }

        @WithMockUser(username = "subscriber", authorities = "ADMIN")
        @Test
        void updateWrongUserRefuse() throws Exception {

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            mockMvc.perform(patch(COMMENTS_MAPPING + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }

        @WithMockUser(username = "subscriber1", authorities = "JOURNALIST")
        @Test
        void updateRefuseTest() throws Exception {

            CommentUpdateDto commentUpdateDto = CommentUpdateDtoTestBuilder.createCommentUpdateDto().build();
            mockMvc.perform(patch(COMMENTS_MAPPING + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentUpdateDto)))
                    .andExpect(status().is5xxServerError());
        }
    }


    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on create controller method")
    class TestsOnCreate {
        @WithMockUser(authorities = "ADMIN")
        @Test
        void createIntegrationTest() throws Exception {

            CommentCreateDto commentCreateDto = CommentCreateDtoTestBuilder.createCommentCreateDto().build();

            mockMvc.perform(post(COMMENTS_MAPPING)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentCreateDto)))
                    .andExpect(status().isCreated());
        }

        @Test
        void createWithMockCallSideServiceIntegrationTest() throws Exception {

            String userAuthorityDto = TestSecurityJsonData.readCommentUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));

            CommentCreateDto commentCreateDto = CommentCreateDtoTestBuilder.createCommentCreateDto().build();

            mockMvc.perform(post(COMMENTS_MAPPING)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("authorization", "Bearer " + TOKEN)
                            .content(objectMapper.writeValueAsString(commentCreateDto)))
                    .andExpect(status().isCreated());
        }

        @WithMockUser(authorities = "SUBSCRIBER")
        @Test
        void wrongInputCreateIntegrationTest() throws Exception {

            CommentCreateDto commentCreateDto = CommentCreateDtoTestBuilder.createCommentCreateDto().withNewsId(null).build();

            mockMvc.perform(post(COMMENTS_MAPPING)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentCreateDto)))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @WireMockTest(httpPort = 7999)
    @DisplayName("Tests on delete controller method")
    class TestsOnDelete {
        @WithMockUser(username = "subscriber1", authorities = "ADMIN")
        @Test
        void deleteByAdminTest() throws Exception {

            mockMvc.perform(delete(COMMENTS_MAPPING + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "subscriber1", authorities = "ADMIN")
        @Test
        void deleteWithMockCallSideServiceByAdminTest() throws Exception {
            String userAuthorityDto = TestSecurityJsonData.readCommentUserAuthorityDto();

            stubFor(
                    WireMock.get(urlPathEqualTo(USERS_CLIENT_MAPPING + "/validate"))
                            .willReturn(aResponse()
                                    .withBody(userAuthorityDto)
                                    .withStatus(200)
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())));

            mockMvc.perform(delete(COMMENTS_MAPPING + "/{id}", 2)
                            .header("authorization", "Bearer " + TOKEN)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "subscriber1", authorities = "SUBSCRIBER")
        @Test
        void deleteBySubscriberTest() throws Exception {

            mockMvc.perform(delete(COMMENTS_MAPPING + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @WithMockUser(username = "subscriber1", authorities = "JOURNALIST")
        @Test
        void deleteRefuseTest() throws Exception {

            mockMvc.perform(delete(COMMENTS_MAPPING + "/{id}", 1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is5xxServerError());
        }
    }
}

