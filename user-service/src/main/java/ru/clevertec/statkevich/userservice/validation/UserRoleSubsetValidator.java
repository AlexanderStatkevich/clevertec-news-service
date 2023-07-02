package ru.clevertec.statkevich.userservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.clevertec.statkevich.userservice.domain.UserRole;

import java.util.Arrays;


public class UserRoleSubsetValidator implements ConstraintValidator<UserRoleSubset, UserRole> {
    private UserRole[] subset;

    @Override
    public void initialize(UserRoleSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(UserRole value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
