package ru.clevertec.statkevich.newsservice;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Transactional
@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
public class BaseTest {

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3-alpine");

    static {
        postgreSQLContainer
                .withReuse(true)
                .start();
    }
}
