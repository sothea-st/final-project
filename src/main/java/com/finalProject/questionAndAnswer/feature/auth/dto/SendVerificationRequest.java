package com.finalProject.questionAndAnswer.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SendVerificationRequest(
        @NotBlank(message = "Email is required")
        @Pattern(regexp = "^[\\w.%+-]+@gmail\\.com$", message = "Email must be a valid Gmail address")
        String email
) {
}
