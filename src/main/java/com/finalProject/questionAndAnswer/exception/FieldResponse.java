package com.finalProject.questionAndAnswer.exception;

import lombok.Builder;

@Builder
public record FieldResponse(
        String field,
        String detail
) {
}
