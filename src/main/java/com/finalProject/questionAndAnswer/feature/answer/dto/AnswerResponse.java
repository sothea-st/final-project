package com.finalProject.questionAndAnswer.feature.answer.dto;

import lombok.Builder;

@Builder
public record AnswerResponse(
        String answer,
        String snippedCode,
        String userName,
        String profileImage
) {
}
