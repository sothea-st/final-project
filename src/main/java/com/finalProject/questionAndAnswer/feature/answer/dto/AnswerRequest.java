package com.finalProject.questionAndAnswer.feature.answer.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerRequest(
        @NotBlank(message = "the field uuidUser is required")
        String uuidUser,
        @NotBlank(message = "the field uuidQuestion is required")
        String uuidQuestion,
        @NotBlank(message = "the field answer is required")
        String answer,
        String snippedCode
) {
}
