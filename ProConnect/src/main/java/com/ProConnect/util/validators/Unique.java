package com.ProConnect.util.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
    String message() default "Field must be unique";

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String columnName();
    String tableName();
}
