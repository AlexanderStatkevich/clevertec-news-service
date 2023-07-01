package ru.clevertec.statkevich.newsservice.testutil.responsedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class TestNewsControllerData {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
            .deserializerByType(LocalDate.class, new LocalDateDeserializer(DATE_FORMAT))
            .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMAT)).build();

    public static NewsVo buildApiFindByIdResponse() throws IOException {
        String json = load("__files/news_controller/api_find_by_id_response.json");
        return objectMapper.readValue(json, NewsVo.class);
    }

    public static Page<NewsVo> buildApiFindAllResponse() throws IOException {
        String json = load("__files/news_controller/api_find_all_response.json");
        List<NewsVo> newsVoList = objectMapper.readValue(json, new TypeReference<>() {
        });
        return new PageImpl<>(newsVoList, Pageable.ofSize(20), 1);
    }

    private static String load(String fileName) throws IOException {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(fileName);
        return IOUtils.toString(Objects.requireNonNull(systemResourceAsStream), "UTF-8");
    }

}
