package com.example.manager.exception;

import com.example.manager.data.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PersonDataExceptionHandler {

  @Autowired ResponseFormat response;

  // handle custom exceptions from validation annotations...
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseFormat handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    ex.getBindingResult().getFieldErrors().forEach(e -> response.setMessage(e.getDefaultMessage()));
    response.setTimeStamp(System.currentTimeMillis());
    return response;
  }

  // handle all sort of generic exceptions...
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseFormat handleGenericException(Exception ex) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage(ex.getMessage());
    response.setTimeStamp(System.currentTimeMillis());
    return response;
  }
}
