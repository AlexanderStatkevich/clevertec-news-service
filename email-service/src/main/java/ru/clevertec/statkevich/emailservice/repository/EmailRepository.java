package ru.clevertec.statkevich.emailservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.statkevich.emailservice.domain.EmailVerification;

public interface EmailRepository extends JpaRepository<EmailVerification, Long> {

    Boolean existsByEmailAndVerificationCode(String email, String verificationCode);

}
