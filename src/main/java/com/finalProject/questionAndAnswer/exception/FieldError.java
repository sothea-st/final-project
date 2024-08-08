package com.finalProject.questionAndAnswer.exception;

import lombok.Builder;

@Builder
public record FieldError<T>(
        int status,
        T reason
) {
}
