package com.plantaccion.alartapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "^[0-9]+$", message = "Only numbers are allowed")
public @interface OnlyNumbers {
    String message() default "Only numbers are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}