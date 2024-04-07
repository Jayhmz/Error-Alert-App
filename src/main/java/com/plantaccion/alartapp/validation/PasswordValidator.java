package com.plantaccion.alartapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$!%^&+=])(?!.*\\s).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    @Override
    public void initialize(StrongPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && pattern.matcher(password).matches();
    }
}
