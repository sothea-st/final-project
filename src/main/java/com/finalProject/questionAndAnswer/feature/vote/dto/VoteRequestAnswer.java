package com.finalProject.questionAndAnswer.feature.vote.dto;

import jakarta.validation.constraints.NotBlank;

public record VoteRequestAnswer(
        @NotBlank(message = "The field uuidUser is required.")
        String uuidUser,
        @NotBlank(message = "The field uuidAnswer is required.")
        String uuidAnswer
) {
}
