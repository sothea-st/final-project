package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import lombok.Builder;

@Builder
public record CommentResponse(
        String comment,
        String userComment,
        String profileImage
) {
}
