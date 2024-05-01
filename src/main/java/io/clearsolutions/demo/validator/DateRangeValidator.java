package io.clearsolutions.demo.validator;

import io.clearsolutions.demo.annotation.ValidatedDateRange;
import io.clearsolutions.demo.dto.DateRangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidatedDateRange, DateRangeDto> {
    /**
     * Validates if the given date range is valid, i.e., if the start date is before the end date.
     *
     * @param dateRange The date range to be validated.
     * @param context   The validation context.
     * @return true if the date range is valid, false otherwise.
     */
    @Override
    public boolean isValid(DateRangeDto dateRange, ConstraintValidatorContext context) {
        if (dateRange == null) {
            return true;
        }
        return dateRange.getFrom().isBefore(dateRange.getTo());
    }
}
