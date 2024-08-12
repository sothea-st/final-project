package com.finalProject.questionAndAnswer.feature.questions.dto;

import com.finalProject.questionAndAnswer.utils.ResponseLink;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionResponse(
        String title,
        String content,
        String snippedCode,
        String uuidQuestion,
        List<ResponseLink> links,
        List<ImageResponse> images
) {
}
