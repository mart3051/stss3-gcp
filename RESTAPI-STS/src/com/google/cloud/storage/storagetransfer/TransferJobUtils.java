package com.google.cloud.storage.storagetransfer;

import com.google.api.services.storagetransfer.v1.model.Date;
import com.google.api.services.storagetransfer.v1.model.TimeOfDay;

/**
 * Utility methods for creating TransferJobs.
 *
 */
public final class TransferJobUtils {
  private static final int BASE_10 = 10;

  /**
   * A private constructor.
   */
  private TransferJobUtils() {}

  /**
   * Parses a Date from a string of the form "YYYY-MM-DD".
   *
   * @param dateString
   *          a string of the form "YYYY-MM-DD"
   * @return a Google Date representing the desired date
   * @throws NumberFormatException
   *           if the input string has an incorrect format
   * @throws InstantiationException
   *           if Date object instantiation failed
   * @throws IllegalAccessException
   *           if Date object cannot be accessed
   */
  public static Date createDate(String dateString)
      throws NumberFormatException, InstantiationException, IllegalAccessException {
    Date date =
        Date.class
            .newInstance()
            .setYear(Integer.parseInt(dateString.split("-")[0], BASE_10))
            .setMonth(Integer.parseInt(dateString.split("-")[1], BASE_10))
            .setDay(Integer.parseInt(dateString.split("-")[2], BASE_10));
    return date;
  }

  /**
   * Parses a TimeOfDay from a string of the form "HH:MM:SS".
   *
   * @param timeString
   *          a string of the form "HH:MM:SS"
   * @return a TimeOfDay representing the desired time
   * @throws NumberFormatException
   *           if the input string has an incorrect format
   * @throws InstantiationException
   *           if Date object instantiation failed
   * @throws IllegalAccessException
   *           if Date object cannot be accessed
   */
  public static TimeOfDay createTimeOfDay(String timeString)
      throws NumberFormatException, InstantiationException, IllegalAccessException {
    TimeOfDay time =
        TimeOfDay.class
            .newInstance()
            .setHours(Integer.parseInt(timeString.split(":")[0], BASE_10))
            .setMinutes(Integer.parseInt(timeString.split(":")[1], BASE_10))
            .setSeconds(Integer.parseInt(timeString.split(":")[2], BASE_10));
    return time;
  }

  public static String getEnvOrFail(String envName) {
    String envValue = System.getenv(envName);
    if (envValue == null || envValue.isEmpty()) {
      System.err.printf("The %s environment variable must be set.", envName);
      System.exit(1);
    }
    return envValue;
  }

  public static String getPropertyOrFail(String propertyName) {
    String propertyValue = System.getProperty(propertyName);
    if (propertyValue == null || propertyValue.isEmpty()) {
      System.err.printf("The %s property must be set.", propertyName);
      System.exit(1);
    }
    return propertyValue;
  }
}