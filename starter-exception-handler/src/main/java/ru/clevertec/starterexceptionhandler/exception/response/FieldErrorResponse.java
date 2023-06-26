package ru.clevertec.starterexceptionhandler.exception.response;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class FieldErrorResponse {
    private final String field;
    private final String message;

    public FieldErrorResponse(ObjectError objectError) {
        this.message = objectError.getDefaultMessage();

        if (objectError instanceof FieldError) {
            this.field = ((FieldError) objectError).getField();
        } else {
            this.field = "";
        }
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
