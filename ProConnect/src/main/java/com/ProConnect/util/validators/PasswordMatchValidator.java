package com.ProConnect.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    private static final Logger logger = LoggerFactory.getLogger(PasswordMatchValidator.class);

    private String passwordFieldName;
    private String passwordMatchFieldName;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        passwordFieldName = constraintAnnotation.password();
        passwordMatchFieldName = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Class<?> clazz = o.getClass();
            Field passwordField = clazz.getDeclaredField(passwordFieldName);
            Field passwordMatchField = clazz.getDeclaredField(passwordMatchFieldName);
            passwordField.setAccessible(true);
            passwordMatchField.setAccessible(true);

            String password = (String) passwordField.get(o);
            String passwordMatch = (String) passwordMatchField.get(o);

            return password != null && password.equals(passwordMatch);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Reflection error in PasswordMatchValidator", e);
            return false;
        }
    }
}
