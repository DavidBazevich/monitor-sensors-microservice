package org.senla.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.senla.entity.Range;

public class RangeValidator implements ConstraintValidator<RangeVal, Range> {

    @Override
    public boolean isValid(Range range, ConstraintValidatorContext constraintValidatorContext) {
        Integer from = range.getRange_from();
        Integer to = range.getRange_to();
        if (from != null && to != null
                && from < to
                && from > 0){
            return true;
        }
        return false;
    }
}
