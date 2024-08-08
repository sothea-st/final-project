package com.finalProject.questionAndAnswer.utils;

import java.security.SecureRandom;

public class RandomUtil {

    public static String randomUtil6Digit(){
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(900000)+"";
    }
}
