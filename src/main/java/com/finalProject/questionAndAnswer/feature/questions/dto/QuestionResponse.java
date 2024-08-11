package com.finalProject.questionAndAnswer.feature.questions.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record QuestionResponse(
        String title,
        String content,
        String snippedCode,
        String uuidQuestion
) {
}
