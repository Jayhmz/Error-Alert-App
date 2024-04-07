package com.plantaccion.alartapp.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyNumbersValidator implements ConstraintValidator<OnlyNumbers, String> {

    @Override
    public void initialize(OnlyNumbers constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the value is null or empty
        if (value == null || value.isEmpty()) {
            return true; // Let @NotBlank or @NotNull handle empty cases
        }
        return value.matches("^[0-9]+$");
    }
}
