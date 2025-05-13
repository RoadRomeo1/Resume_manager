package com.example.manager.data;

import com.example.manager.data.constants.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseFormat {

  private int status;
  private String message;
  private long timeStamp;

  public static ApiResponse toApiResponse(ResponseFormat response) {
    return new ApiResponse(
        response.getStatus() >= 200 && response.getStatus() < 300,
        response.getMessage(),
        null,
        null,
        java.time.LocalDateTime.now());
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }
}
