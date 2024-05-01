package io.clearsolutions.demo.validator;

import io.clearsolutions.demo.annotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
    @Value("${user-min-age}")
    private int minAge;

    /**
     * Validates if the given date meets the minimum age requirement.
     *
     * @param value   The date to be validated.
     * @param context The validation context.
     * @return true if the date meets the minimum age requirement, false otherwise.
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Period.between(value, LocalDate.now()).getYears() >= minAge;
    }
}