package com.finalProject.questionAndAnswer.feature.comment.dto;

import lombok.Builder;

@Builder
public record CommentResponse(
        String comment,
        String uuidComment,
        String userComment,
        String profile,
        String postDate
) {
}
