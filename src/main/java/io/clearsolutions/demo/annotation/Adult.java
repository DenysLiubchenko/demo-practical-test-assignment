package io.clearsolutions.demo.annotation;

import io.clearsolutions.demo.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    String message() default "User must be an adult";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}