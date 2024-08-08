package com.finalProject.questionAndAnswer.constant;

import java.time.Instant;

public class UUIDConstant {

    public static Long UUID() {
        Instant now = Instant.now();
        return now.getEpochSecond();
    }


}
