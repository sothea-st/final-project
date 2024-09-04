package com.finalProject.questionAndAnswer.feature.password_reset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordResetRequest(
        @NotBlank(message = "The filed email is required.")
        @Pattern(regexp = "^[\\w.%+-]+@gmail\\.com$", message = "Email must be a valid Gmail address")
        String email
) {
}
