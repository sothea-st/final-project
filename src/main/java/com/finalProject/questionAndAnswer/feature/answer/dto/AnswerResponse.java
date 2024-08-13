package com.finalProject.questionAndAnswer.feature.answer.dto;

import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

import java.util.List;

@Builder
public record AnswerResponse(
        String answer,
        String snippedCode,
        String userName,
        String profileImage,
        String uuidAnswer,
        List<ResponseLink> link,
        List<ImageResponse> image
) {
}
