package com.finalProject.questionAndAnswer.feature.image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

@Builder
public record ImageResponse(
        String name,
        String uuidImage,
        String url,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ResponseLink link
) {
}
