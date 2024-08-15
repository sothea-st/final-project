package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

import java.util.List;
@Builder
public record PublicQuestionResponse(
        String title,
        String content,
        String uuidQuestion,
        String postDate,
        ResponseLink link
) {
}
