package com.finalProject.questionAndAnswer.response_success;

import lombok.Builder;

@Builder
public record JavaResponse<T>(
        int status,
        String msg,
        T data
) {
    public JavaResponse {
        if (status == 0) status = 200; // Default status
        if (msg == null) msg = "success"; // Default message
    }
}
