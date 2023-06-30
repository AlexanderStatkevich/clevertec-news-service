package ru.clevertec.statkevich.newsservice.integration;


import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@Sql("classpath:db/data.sql")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class BaseIntegrationTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3-alpine");

    @BeforeAll
    static void init() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }
}
