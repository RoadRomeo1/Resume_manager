package com.example.manager.config;

import com.example.manager.data.constants.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public ErrorAttributes errorAttributes() {
    return new DefaultErrorAttributes() {
      @Override
      public Map<String, Object> getErrorAttributes(
          WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        // Remove sensitive information
        errorAttributes.remove("trace");
        return errorAttributes;
      }
    };
  }

  @RestController
  public static class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final MessageSource messageSource;

    public CustomErrorController(ErrorAttributes errorAttributes, MessageSource messageSource) {
      this.errorAttributes = errorAttributes;
      this.messageSource = messageSource;
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiResponse> handleError(
        HttpServletRequest request, WebRequest webRequest) {
      Throwable error = this.errorAttributes.getError(webRequest);

      // If there's no error, return a generic error response
      if (error == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(
                    "An unexpected error occurred",
                    "An unexpected error occurred. Please try again later.",
                    request.getRequestURI()));
      }

      // Handle validation errors
      if (error instanceof MethodArgumentNotValidException) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) error;
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors =
            bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    messageSource.getMessage(
                        "error.validation.failed", null, LocaleContextHolder.getLocale()),
                    errors));
      }

      if (error instanceof ConstraintViolationException) {
        ConstraintViolationException ex = (ConstraintViolationException) error;
        List<String> errors =
            ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    messageSource.getMessage(
                        "error.validation.failed", null, LocaleContextHolder.getLocale()),
                    errors));
      }

      if (error instanceof HttpMessageNotReadableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    messageSource.getMessage(
                        "error.invalid.request", null, LocaleContextHolder.getLocale()),
                    "Please check your request format and try again",
                    request.getRequestURI()));
      }

      // Handle other errors
      Map<String, Object> errorAttributes =
          this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

      HttpStatus status = HttpStatus.valueOf((Integer) errorAttributes.get("status"));
      String errorMessage = (String) errorAttributes.get("error");
      String message = (String) errorAttributes.get("message");
      String path = (String) errorAttributes.get("path");

      // If no specific message is available, use a generic one based on status
      if (message == null || message.equals("No message available")) {
        message =
            status.is4xxClientError()
                ? "The request could not be processed. Please check your input."
                : "An unexpected error occurred. Please try again later.";
      }

      return ResponseEntity.status(status).body(ApiResponse.error(errorMessage, message, path));
    }
  }
}
