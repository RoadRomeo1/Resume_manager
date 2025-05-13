package com.example.manager.exception;

import com.example.manager.data.Project;
import com.example.manager.data.constants.ApiResponse;
import com.example.manager.exception.address.AddressNotFoundException;
import com.example.manager.exception.person.PersonNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Global exception handler to provide user-friendly error messages */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Autowired private MessageSource messageSource;
  @Autowired private HttpServletRequest request;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                error -> {
                  String fieldName = error.getField().replaceAll("\\[\\d*\\]", "");
                  String message =
                      messageSource.getMessage(
                          error.getDefaultMessage(),
                          new Object[] {Project.getCurrentYear()},
                          LocaleContextHolder.getLocale());
                  return fieldName + ": " + message;
                })
            .collect(Collectors.toList());

    logger.debug("Validation failed: {}", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.error(
                messageSource.getMessage(
                    "error.validation.failed", null, LocaleContextHolder.getLocale()),
                errors,
                request.getRequestURI()));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    logger.debug("Invalid request format", ex);

    if (ex.getCause() instanceof InvalidFormatException) {
      InvalidFormatException ife = (InvalidFormatException) ex.getCause();
      String fieldName = ife.getPath().get(ife.getPath().size() - 1).getFieldName();
      fieldName = fieldName.replaceAll("\\[\\d*\\]", "");
      String invalidValue = ife.getValue().toString();

      // Handle enum types
      if (ife.getTargetType().isEnum()) {
        String validValues =
            Arrays.stream(ife.getTargetType().getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String message =
            messageSource.getMessage(
                "error.enum.invalid",
                new Object[] {invalidValue, fieldName, validValues},
                LocaleContextHolder.getLocale());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    "Invalid value for " + fieldName, message, request.getRequestURI()));
      }

      // Handle numeric types
      if (Number.class.isAssignableFrom(ife.getTargetType())) {
        String message =
            messageSource.getMessage(
                "error.number.invalid",
                new Object[] {invalidValue, fieldName},
                LocaleContextHolder.getLocale());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error("Invalid numeric value", message, request.getRequestURI()));
      }

      // Handle boolean types
      if (ife.getTargetType().equals(Boolean.class)) {
        String message =
            messageSource.getMessage(
                "error.boolean.invalid",
                new Object[] {invalidValue, fieldName},
                LocaleContextHolder.getLocale());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error("Invalid boolean value", message, request.getRequestURI()));
      }

      // Handle date/time types
      if (ife.getTargetType().getName().contains("Date")
          || ife.getTargetType().getName().contains("Time")) {
        String messageKey =
            ife.getTargetType().getName().contains("Time")
                    && !ife.getTargetType().getName().contains("Date")
                ? "error.time.invalid"
                : ife.getTargetType().getName().contains("Date")
                        && !ife.getTargetType().getName().contains("Time")
                    ? "error.date.invalid"
                    : "error.datetime.invalid";

        String message =
            messageSource.getMessage(
                messageKey,
                new Object[] {invalidValue, fieldName},
                LocaleContextHolder.getLocale());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error("Invalid date/time value", message, request.getRequestURI()));
      }
    }

    if (ex.getCause() instanceof MismatchedInputException) {
      MismatchedInputException mie = (MismatchedInputException) ex.getCause();
      String fieldName = mie.getPath().get(mie.getPath().size() - 1).getFieldName();
      fieldName = fieldName.replaceAll("\\[\\d*\\]", "");

      String message =
          messageSource.getMessage(
              "error.string.invalid", new Object[] {fieldName}, LocaleContextHolder.getLocale());

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(
              ApiResponse.error(
                  "Invalid value for " + fieldName, message, request.getRequestURI()));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.error(
                messageSource.getMessage(
                    "error.invalid.request", null, LocaleContextHolder.getLocale()),
                "Please check your request format and try again",
                request.getRequestURI()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {
    List<String> errors =
        ex.getConstraintViolations().stream()
            .map(
                violation -> {
                  String fieldName =
                      violation.getPropertyPath().toString().replaceAll("\\[\\d*\\]", "");
                  String message =
                      messageSource.getMessage(
                          violation.getMessage(),
                          new Object[] {Project.getCurrentYear()},
                          violation.getMessage(),
                          LocaleContextHolder.getLocale());
                  return fieldName + ": " + message;
                })
            .collect(Collectors.toList());

    logger.debug("Constraint violation: {}", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.error(
                messageSource.getMessage(
                    "error.validation.failed", null, LocaleContextHolder.getLocale()),
                errors,
                request.getRequestURI()));
  }

  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<ApiResponse> handlePersonNotFound(PersonNotFoundException ex) {
    logger.debug("Person not found: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ApiResponse.error(
                messageSource.getMessage(
                    "error.person.not.found",
                    new Object[] {ex.getMessage()},
                    LocaleContextHolder.getLocale()),
                ex.getMessage(),
                request.getRequestURI()));
  }

  @ExceptionHandler(AddressNotFoundException.class)
  public ResponseEntity<ApiResponse> handleAddressNotFound(AddressNotFoundException ex) {
    logger.debug("Address not found: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ApiResponse.error(
                messageSource.getMessage(
                    "error.address.not.found",
                    new Object[] {ex.getMessage()},
                    LocaleContextHolder.getLocale()),
                ex.getMessage(),
                request.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
    logger.error("Unexpected error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ApiResponse.error(
                messageSource.getMessage("error.unexpected", null, LocaleContextHolder.getLocale()),
                "An unexpected error occurred. Please try again later."));
  }
}
