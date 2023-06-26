package ru.clevertec.starterexceptionhandler.exception.response;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ErrorResponseUtils {

    private ErrorResponseUtils() {
    }

    public static StructuredErrorResponse buildStructuredObjectErrorResponse(List<ObjectError> errors) {
        List<ErrorResponseField> errorResponseFields = errors.stream()
                .map(ErrorResponseUtils::buildErrorResponseFiled)
                .collect(Collectors.toList());
        return new StructuredErrorResponse(errorResponseFields);
    }

    public static StructuredErrorResponse buildStructuredConstraintViolationResponse(Collection<ConstraintViolation<?>> errors) {
        List<ErrorResponseField> errorResponseFields = errors.stream()
                .map(ErrorResponseUtils::buildErrorResponseFiled)
                .collect(Collectors.toList());
        return new StructuredErrorResponse(errorResponseFields);
    }

    private static ErrorResponseField buildErrorResponseFiled(ObjectError objectError) {
        String message = objectError.getDefaultMessage();
        String field = objectError instanceof FieldError
                ? ((FieldError) objectError).getField()
                : "";
        return new ErrorResponseField(field, message);
    }

    private static ErrorResponseField buildErrorResponseFiled(ConstraintViolation<?> constraintViolation) {
        String message = constraintViolation.getMessage();
        String field = constraintViolation.getPropertyPath().toString();
        return new ErrorResponseField(field, message);
    }
}
