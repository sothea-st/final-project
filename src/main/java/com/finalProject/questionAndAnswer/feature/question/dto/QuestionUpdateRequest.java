package com.finalProject.questionAndAnswer.feature.question.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record QuestionUpdateRequest(
        @NotBlank(message = "The field title is required.")
        String title,

        @NotBlank(message = "The field content is required.")
        String content,

        String snippedCode,

        List<String> images
) {
}
