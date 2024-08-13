package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

import java.util.List;
@Builder
public record PublicQuestionResponse(
        String title,
        String content,
        String snippedCode,
        String uuidQuestion,
        String postDate,
        AuthorResponse author,
        ResponseLink link,
        List<ImageResponse> image
) {
}
