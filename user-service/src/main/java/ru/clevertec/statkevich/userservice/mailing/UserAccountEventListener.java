package ru.clevertec.statkevich.userservice.mailing;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserAccountEventListener {

    private final UserAccountEmailService emailService;

    public UserAccountEventListener(UserAccountEmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void onUserCreation(UserRegisteredEvent event) {
        String email = event.email();
        String fullName = event.fullName();
        emailService.sendEmail(email, fullName);
    }
}
