package com.finalProject.questionAndAnswer.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaConstant {

    public static String dateFormat(String dateTimeStr) {
        // If the input includes milliseconds, remove them
        if (dateTimeStr.length() > 19) {
            dateTimeStr = dateTimeStr.substring(0, 19);
        }
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, originalFormatter);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(targetFormatter);
    }
}
