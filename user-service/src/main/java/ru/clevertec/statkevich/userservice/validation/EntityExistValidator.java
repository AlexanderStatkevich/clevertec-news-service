package ru.clevertec.statkevich.userservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.clevertec.statkevich.userservice.manage.UserManageService;


public class EntityExistValidator implements ConstraintValidator<UniqueEntity, String> {

    private final UserManageService userManageService;

    public EntityExistValidator(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    @Override
    public void initialize(UniqueEntity contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return userManageService.notExistsByEmail(contactField);
    }
}
