package com.example.demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Util for parsing and formatting dates
 */
public class DateUtil {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String DATE_PATTERN2 = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER2 = DateTimeFormatter.ofPattern(DATE_PATTERN2);

    /**
     * format LocalDate to look like dd.MM.yyyy
     * @param date LocalDate value of the date
     * @return String result of formatting
     */
    public static String formatForPeople(LocalDate date){
        if (date == null){
            return null;
        }
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * format LocalDate to look like yyyy-MM-dd
     * @param date LocalDate value of the date
     * @return String result of formatting
     */
    public static String format(LocalDate date){
        if (date == null){
            return null;
        }
        return DATE_TIME_FORMATTER2.format(date);
    }

    /**
     * Parse date from String (in both yyyy-MM-dd and dd.MM.yyyy) and convert it to LocalDate
     * @param dateString String value of the date
     * @return LocalDate date parsed
     */
    public static LocalDate parse(String dateString){
        try{
            if (dateString.contains("-")){
                return DATE_TIME_FORMATTER2.parse(dateString,LocalDate::from);
            }else{
                String newDate = dateString.replace(".", "-");
                return DATE_TIME_FORMATTER2.parse(String.valueOf(new char[]{newDate.charAt(6),
                        newDate.charAt(7), newDate.charAt(8), newDate.charAt(9), newDate.charAt(5),
                        newDate.charAt(3), newDate.charAt(4), newDate.charAt(2), newDate.charAt(0),
                        newDate.charAt(1)}), LocalDate::from);
            }
        }
        catch (DateTimeParseException e){
            return null;
        }
    }

    /**
     * Validation of the date to be correct
     * @param dateString String value of the date
     * @return boolean answer
     */
    public static boolean validDate(String dateString){
        return DateUtil.parse(dateString) != null;
    }
}