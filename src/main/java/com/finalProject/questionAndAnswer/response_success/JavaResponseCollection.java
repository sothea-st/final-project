package com.finalProject.questionAndAnswer.response_success;

import lombok.Builder;

@Builder
public record JavaResponseCollection<T>(
        int status,
        String msg,
        long count,
        T data
) {
    public JavaResponseCollection {
        if (status == 0) status = 200; // Default status
        if (msg == null) msg = "success"; // Default message
    }
}
