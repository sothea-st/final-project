package com.finalProject.questionAndAnswer.feature.publicQuestion.dto;

import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

@Builder
public record AuthorResponse(
        String uuidUser,
        String name,
        ResponseLink link
) {
}
