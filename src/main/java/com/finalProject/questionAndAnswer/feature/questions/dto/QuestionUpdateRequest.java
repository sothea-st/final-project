package com.finalProject.questionAndAnswer.feature.questions.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionUpdateRequest(
        @NotBlank(message = "The field title is required.")
        String title,

        @NotBlank(message = "The field content is required.")
        String content,

        String snippedCode
) {
}
