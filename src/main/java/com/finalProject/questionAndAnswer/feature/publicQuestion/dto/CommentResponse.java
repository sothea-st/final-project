package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import lombok.Builder;

@Builder
public record CommentResponse(
        String comment,
        String uuidComment,
        String userComment,
        String uuidUser,
        String profileImage,
        String postDate
) {
}
