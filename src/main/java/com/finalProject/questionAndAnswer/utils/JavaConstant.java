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

    public static String maskEmail(String email) {
        // Split the email into two parts: before and after the '@'
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // Mask the username part
        String maskedUsername = username.substring(0, 2) + "**********";

        // Mask the domain part
        String[] domainParts = domain.split("\\.");
        String maskedDomain = domainParts[0].substring(0, 1) + "****";

        // Combine the masked parts
        return maskedUsername + "@" + maskedDomain + "." + domainParts[1];
    }
}
