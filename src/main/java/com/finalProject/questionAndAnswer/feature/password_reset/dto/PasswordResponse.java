package com.finalProject.questionAndAnswer.feature.password_reset.dto;

import lombok.Builder;

@Builder
public record PasswordResponse(
        String url
) {
}
