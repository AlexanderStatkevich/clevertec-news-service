package ru.clevertec.statkevich.emailservice.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

@Service
public class TemplateProcessService {
    private static final String EMAIL_TEMPLATE = "email-template";
    private final SpringTemplateEngine templateEngine;

    public TemplateProcessService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String fillTemplate(Map<String, Object> model) {
        Context context = new Context(Locale.getDefault(), model);
        return templateEngine.process(EMAIL_TEMPLATE, context);
    }
}
