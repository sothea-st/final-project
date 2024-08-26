package com.finalProject.questionAndAnswer.feature.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record CommentResponse(
        String comment,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String uuidComment,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userComment,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String profile,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String postDate
) {
}
