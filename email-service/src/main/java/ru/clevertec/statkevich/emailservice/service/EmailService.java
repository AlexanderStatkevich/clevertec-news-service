package ru.clevertec.statkevich.emailservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.statkevich.emailservice.domain.EmailVerification;
import ru.clevertec.statkevich.emailservice.dto.EmailDto;
import ru.clevertec.statkevich.emailservice.dto.EmailVerificationDto;
import ru.clevertec.statkevich.emailservice.repository.EmailRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailService {

    public static final String SUBJECT = "Email verification";
    public static final String VERIFICATION_CODE_MODEL_KEY = "code";
    public static final String EMAIL_MODEL_KEY = "email";

    private final EmailSendingService emailSendingService;
    private final TemplateProcessService templateProcessService;
    private final EmailRepository emailRepository;


    public void send(EmailDto emailDto) {
        String email = emailDto.email();
        String verificationCode = CodeGenerationUtil.generateCode();
        EmailVerification emailVerification = new EmailVerification(email, verificationCode);
        emailRepository.save(emailVerification);

        Map<String, Object> model = emailDto.model();
        model.put(VERIFICATION_CODE_MODEL_KEY, verificationCode);
        model.put(EMAIL_MODEL_KEY, email);
        String html = templateProcessService.fillTemplate(model);

        emailSendingService.sendEmail(email, SUBJECT, html);
    }


    public boolean verify(EmailVerificationDto emailVerificationDto) {
        String email = emailVerificationDto.email();
        String code = emailVerificationDto.code();
        return emailRepository.existsByEmailAndVerificationCode(email, code);
    }
}
