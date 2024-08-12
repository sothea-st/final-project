package com.finalProject.questionAndAnswer.feature.image.dto;

import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.Builder;

@Builder
public record ImageResponse(
        String name,
        String uuidImage,
        String url,
        ResponseLink links
) {
}
