package ru.clevertec.statkevich.userservice.exception.response;

public class ErrorResponseField {
    private final String field;
    private final String message;

    public ErrorResponseField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
