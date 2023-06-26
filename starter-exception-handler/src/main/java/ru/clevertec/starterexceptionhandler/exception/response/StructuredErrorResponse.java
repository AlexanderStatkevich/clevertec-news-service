package ru.clevertec.starterexceptionhandler.exception.response;

import java.util.List;

public class StructuredErrorResponse {

    private final List<ErrorResponseField> errors;

    public StructuredErrorResponse(List<ErrorResponseField> errors) {
        this.errors = errors;
    }

    public List<ErrorResponseField> getErrors() {
        return errors;
    }
}

