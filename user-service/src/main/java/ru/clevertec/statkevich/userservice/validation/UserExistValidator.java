package ru.clevertec.statkevich.userservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.clevertec.statkevich.userservice.manage.UserManageService;


public class UserExistValidator implements ConstraintValidator<UserExist, String> {

    private final UserManageService userManageService;

    public UserExistValidator(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    @Override
    public void initialize(UserExist contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return !userManageService.notExistsByEmail(contactField);
    }
}
