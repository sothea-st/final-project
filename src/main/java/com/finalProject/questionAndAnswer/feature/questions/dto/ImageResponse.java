package com.finalProject.questionAndAnswer.feature.questions.dto;

import lombok.Builder;

@Builder
public record ImageResponse(
        String name,
        String uuidImage,
        String url
) {
}
