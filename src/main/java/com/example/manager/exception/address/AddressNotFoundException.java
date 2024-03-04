package com.example.manager.exception.address;

public class AddressNotFoundException extends Exception {
  public AddressNotFoundException(String message) {
    super(message);
  }

  public AddressNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AddressNotFoundException(Throwable cause) {
    super(cause);
  }
}
