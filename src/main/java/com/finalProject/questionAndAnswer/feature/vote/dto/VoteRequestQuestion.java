package com.finalProject.questionAndAnswer.feature.vote.dto;

import jakarta.validation.constraints.NotBlank;

public record VoteRequestQuestion(
        @NotBlank(message = "The field uuidUser is required.")
        String uuidUser,

        @NotBlank(message = "The field uuidQuestion is required.")
        String uuidQuestion

) {
}
