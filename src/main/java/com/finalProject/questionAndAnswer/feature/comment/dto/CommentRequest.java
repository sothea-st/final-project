package com.finalProject.questionAndAnswer.feature.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "The field uuidQuestion is required")
        String uuidQuestion,
        @NotBlank(message = "The field comment is required")
        @Size(min = 5,message = "The field comment required at least 5 letters")
        String comment,
        @NotBlank(message = "The field uuidQuestion is required")
        String uuidUser
) {
}
