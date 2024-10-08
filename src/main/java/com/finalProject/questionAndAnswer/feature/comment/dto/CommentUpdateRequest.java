package com.finalProject.questionAndAnswer.feature.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequest(
        @NotBlank(message = "The field comment is required")
        @Size(min = 5,message = "The field comment required at least 5 letters")
        String comment
) {
}
