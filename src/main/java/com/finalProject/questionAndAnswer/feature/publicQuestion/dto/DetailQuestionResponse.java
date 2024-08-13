package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

import java.util.List;

@Builder
public record DetailQuestionResponse(
        String title,
        String content,
        String snippedCode,
        String uuidQuestion,
        String postDate,
        AuthorResponse author,
        List<ImageResponse> image,
        List<CommentResponse> comment,
        List<AnswerResponse> answer
) {
}
