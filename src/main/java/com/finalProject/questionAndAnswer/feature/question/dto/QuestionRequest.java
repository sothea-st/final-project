package com.finalProject.questionAndAnswer.feature.question.dto;

import jakarta.validation.constraints.NotBlank;


import java.util.List;

public record QuestionRequest(

        @NotBlank(message = "The field uuidAuthor is required.")
        String uuidUser,

        @NotBlank(message = "The field title is required.")
        String title,

        @NotBlank(message = "The field content is required.")
        String content,

        String snippedCode,

        List<String> images
) {
}
