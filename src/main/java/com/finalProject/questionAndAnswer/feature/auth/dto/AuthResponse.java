package com.finalProject.questionAndAnswer.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String tokenType,
        String accessToken,
        String refreshToken
) {
}
