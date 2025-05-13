package com.example.manager.data;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearNotInFutureValidator implements ConstraintValidator<YearNotInFuture, Integer> {
  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) return true; // let @NotNull handle nulls if needed
    int currentYear = Year.now().getValue();
    return value <= currentYear;
  }
}
