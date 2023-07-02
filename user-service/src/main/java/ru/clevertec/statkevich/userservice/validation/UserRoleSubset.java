package ru.clevertec.statkevich.userservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.clevertec.statkevich.userservice.domain.UserRole;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UserRoleSubsetValidator.class)
public @interface UserRoleSubset {
    UserRole[] anyOf();

    String message() default "must be any of JOURNALIST, SUBSCRIBER";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
