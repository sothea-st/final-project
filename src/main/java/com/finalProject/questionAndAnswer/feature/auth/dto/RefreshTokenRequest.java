package com.finalProject.questionAndAnswer.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token can not be null.")
        String refreshToken
) {
}
