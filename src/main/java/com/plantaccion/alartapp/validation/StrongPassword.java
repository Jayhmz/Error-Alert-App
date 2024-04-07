package com.plantaccion.alartapp.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = PasswordValidator.class)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Invalid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
