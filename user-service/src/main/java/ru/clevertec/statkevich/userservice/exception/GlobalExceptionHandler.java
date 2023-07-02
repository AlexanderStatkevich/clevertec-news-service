package ru.clevertec.statkevich.userservice.exception;


import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.statkevich.userservice.exception.response.ErrorResponse;
import ru.clevertec.statkevich.userservice.exception.response.ErrorResponseUtils;
import ru.clevertec.statkevich.userservice.exception.response.StructuredErrorResponse;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<List<ErrorResponse>> handleMissingPathVariableException(MissingPathVariableException ex) {
        String message = ex.getVariableName() + " variable is missing";
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(List.of(errorResponse));
    }


    @ExceptionHandler({OptimisticLockException.class, SQLException.class})
    public ResponseEntity<List<ErrorResponse>> handleGeneralServiceException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(errorResponse));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(errorResponse));
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthException(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(errorResponse));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(errorResponse));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException ex) {
        List<ObjectError> allErrors = ex.getAllErrors();
        StructuredErrorResponse structuredErrorResponse = ErrorResponseUtils.buildStructuredObjectErrorResponse(allErrors);
        return ResponseEntity.badRequest().body(structuredErrorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StructuredErrorResponse structuredErrorResponse = ErrorResponseUtils.buildStructuredConstraintViolationResponse(constraintViolations);
        return ResponseEntity.badRequest().body(structuredErrorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ErrorResponse>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Incorrect input";
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(Collections.singletonList(errorResponse));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(Collections.singletonList(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorResponse>> handleException(Exception ex) {
        String message = "Server was unable to process the request correctly, please contact the administrator";
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(errorResponse));
    }
}
