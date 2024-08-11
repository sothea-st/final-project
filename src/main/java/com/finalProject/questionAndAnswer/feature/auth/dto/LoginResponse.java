package com.finalProject.questionAndAnswer.feature.auth.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record LoginResponse(
        String email,
        String profile,
        String userName,
        String uuidUser,
        AuthResponse token,
        List<String> roles
) {
}
