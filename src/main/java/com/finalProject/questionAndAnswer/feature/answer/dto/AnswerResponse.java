package com.finalProject.questionAndAnswer.feature.answer.dto;


import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.AuthorResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.CommentResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

import java.util.List;

@Builder
public record AnswerResponse(
        String uuidAnswer,
        String answer,
        String snippedCode,
        String postDate,
        Integer vote,
        AuthorResponse author,
        List<ResponseLink> link,
        List<ImageResponse> image,
        List<CommentResponse> comments
) {
}
