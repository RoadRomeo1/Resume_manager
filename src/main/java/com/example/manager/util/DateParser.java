package com.example.manager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateParser {

  private DateParser() {}

  public static Date parseDate(String date) throws DateTimeParseException, ParseException {
    return new SimpleDateFormat("dd/mm/yyyy").parse(date);
  }
}
