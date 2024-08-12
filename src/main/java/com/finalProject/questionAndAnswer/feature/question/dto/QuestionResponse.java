package com.finalProject.questionAndAnswer.feature.question.dto;

import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
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
