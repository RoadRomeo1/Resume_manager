package com.example.manager.exception;

import com.example.manager.data.ResponseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class DataExceptionHandler {
  @Autowired ResponseFormat response;

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
    log.trace("------error at here--------: " + ex.toString());
    log.error(ex.getStackTrace().toString());
    log.error(ex.getCause().toString());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage(ex.getMessage());
    response.setTimeStamp(System.currentTimeMillis());
    return response;
  }
}
