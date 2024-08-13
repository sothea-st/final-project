package com.finalProject.questionAndAnswer.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaConstant {

    public static String dateFormat(String dateTimeStr) {
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, originalFormatter);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(targetFormatter);
    }
}
