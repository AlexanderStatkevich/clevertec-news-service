package ru.clevertec.statkevich.userservice.mailing;

import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.userservice.dto.EmailDto;

import java.util.Map;

/**
 * Class responsible for sending email to user via {@link UserAccountClient}
 */
@Service
public class UserAccountEmailService {

    private final UserAccountClient userAccountClient;

    public UserAccountEmailService(UserAccountClient userAccountClient) {
        this.userAccountClient = userAccountClient;
    }

    public void sendEmail(String email, String fullName) {
        Map<String, Object> model = Map.of("name", fullName, "host", "http://localhost:8080/api/v1/users");

        EmailDto emailDto = new EmailDto(email, model);
        userAccountClient.send(emailDto);
    }
}
