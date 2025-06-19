package com.example.manager.data;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = YearNotBeforeCurrentValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface YearNotBeforeCurrent {
  String message() default "Year must not be before current year";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
