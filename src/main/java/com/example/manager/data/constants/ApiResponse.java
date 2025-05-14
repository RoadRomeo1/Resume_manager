package com.example.manager.data.constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Standardized API response format for both success and error cases */
@Data
@NoArgsConstructor
public class ApiResponse {
  private boolean success;
  private String message;
  private Object data;
  private List<String> errors;
  private LocalDateTime timestamp;
  private String path;

  public static ApiResponse success(String message, Object data) {
    ApiResponse response = new ApiResponse();
    response.success = true;
    response.message = message;
    response.data = data;
    response.timestamp = LocalDateTime.now();
    return response;
  }

  public static ApiResponse error(String message, List<String> errors) {
    ApiResponse response = new ApiResponse();
    response.success = false;
    response.message = message;
    response.errors = errors;
    response.timestamp = LocalDateTime.now();
    return response;
  }

  public static ApiResponse error(String message, String error) {
    List<String> errorList = new ArrayList<>();
    errorList.add(error);
    return error(message, errorList);
  }

  public static ApiResponse error(String message, String error, String path) {
    ApiResponse response = error(message, error);
    response.path = path;
    return response;
  }
}
