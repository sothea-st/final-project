package com.finalProject.questionAndAnswer.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaConstant {

    public static String dateFormat(String dateTimeStr){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(outputFormatter);
    }
}
