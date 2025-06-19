package com.example.manager.exception;

public class EnumValidationException extends RuntimeException {
  private final String enumType;
  private final String invalidValue;
  private final String[] validValues;

  public EnumValidationException(String enumType, String invalidValue, String[] validValues) {
    super(
        String.format(
            "Invalid value '%s' for %s. Valid values are: %s",
            invalidValue, enumType, String.join(", ", validValues)));
    this.enumType = enumType;
    this.invalidValue = invalidValue;
    this.validValues = validValues;
  }

  public String getEnumType() {
    return enumType;
  }

  public String getInvalidValue() {
    return invalidValue;
  }

  public String[] getValidValues() {
    return validValues;
  }
}
