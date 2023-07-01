package ru.clevertec.statkevich.newsservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.statkevich.newsservice.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest extends BaseIntegrationTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;


//    @Test
//    void updateIntegrationTest() throws Exception {
//
//        Comment comment = CommentTestBuilder.createComment().build();
//
//        mockMvc.perform(patch("/api/v1/comments/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(giftCertificateCreateUpdateDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(response)))
//    }

    @Test
    void deleteIntegrationTest() throws Exception {

        mockMvc.perform(delete("/api/v1/comments/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Nested
//    @DisplayName("Tests on create controller method")
//    class TestsOnCreate {
//        @Test
//        void createIntegrationTest() throws Exception {
//            GiftCertificateCreateUpdateDto giftCertificateCreateUpdateDto =
//                    new GiftCertificateCreateUpdateDto("test", "description", BigDecimal.ONE, 25, List.of());
//
//            mockMvc.perform(post("/certificates")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(giftCertificateCreateUpdateDto)))
//                    .andExpect(status().isCreated())
//                    .andExpect(content().string("4"));
//        }
//
//        @Test
//        void wrongInputCreateIntegrationTest() throws Exception {
//            GiftCertificateCreateUpdateDto giftCertificateCreateUpdateDto =
//                    new GiftCertificateCreateUpdateDto("", "description", BigDecimal.ONE, 25, List.of());
//
//            mockMvc.perform(post("/certificates")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(giftCertificateCreateUpdateDto)))
//                    .andExpect(status().isBadRequest());
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests on find controller methods")
//    class TestsOnFind {
//        @Test
//        void findByIdIntegrationTest() throws Exception {
//            GiftCertificateVo response = TestGiftCertificatesControllerData.buildApiFindByIdResponse();
//
//            mockMvc.perform(get("/certificates/{id}", 1)
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(content().string(objectMapper.writeValueAsString(response)));
//        }
//
//        @Test
//        void findByIdWrongInputIntegrationTest() throws Exception {
//
//            mockMvc.perform(get("/certificates/{id}", 6)
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isBadRequest());
//        }
//
//
//        @Test
//        void findAllIntegrationTest() throws Exception {
//            Page<GiftCertificateVo> response = TestGiftCertificatesControllerData.buildApiFindAllResponse();
//
//            mockMvc.perform(get("/certificates")
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(content().string(objectMapper.writeValueAsString(response)));
//        }
//    }


}

