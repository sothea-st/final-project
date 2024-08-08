package com.finalProject.questionAndAnswer.feature.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String message,
        String email
) {
}
